package com.ssmmhh.free2playgames.feature_game.presentation.game_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ssmmhh.free2playgames.R
import com.ssmmhh.free2playgames.databinding.FragmentGameDetailBinding
import com.ssmmhh.free2playgames.feature_game.domain.model.GameDetail
import com.ssmmhh.free2playgames.feature_game.presentation.game_detail.viewstate.GameDetailViewState
import com.ssmmhh.free2playgames.feature_game.presentation.util.TextViewCollapsingAnimation
import com.ssmmhh.free2playgames.feature_game.presentation.util.setVisibilityToGone
import com.ssmmhh.free2playgames.feature_game.presentation.util.setVisibilityToVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameDetailFragment : Fragment() {

    private val viewModel by viewModels<GameDetailViewModel>()

    private var _binding: FragmentGameDetailBinding? = null
    private val binding get() = _binding!!

    private var textViewCollapsingAnimation: TextViewCollapsingAnimation? = null

    private fun setupUi() {
        setupSwipeToRefreshLayout()
        setupOnclickListeners()
        setupTextViewCollapsingAnimation()
        //set on click listener on toolbar back button
        binding.toolbarGameDetail.setNavigationOnClickListener { findNavController().navigateUp() }
    }


    private fun setupTextViewCollapsingAnimation() {
        textViewCollapsingAnimation = TextViewCollapsingAnimation(
            containerView = binding.constraintLayoutDetailContainer,
            textView = binding.txtGameDescription,
            isCollapsedAtInit = viewModel.isGameDescriptionTextViewCollapsed.value,
            animationDuration = DESCRIPTION_TEXTVIEW_COLLAPSING_ANIMATION_DURATION,
            collapsedTextViewMaxLine = DESCRIPTION_TEXTVIEW_COLLAPSED_MAX_LINE
        )
    }

    private fun setupSwipeToRefreshLayout() {
        binding.swipeRefreshGameDetail.setOnRefreshListener {
            viewModel.refreshGameDetail()
            //hide error textView and its error
            binding.txtGameDetailError.setVisibilityToGone()
        }
    }

    private fun setupOnclickListeners() {
        binding.btnPlayNow.setOnClickListener {
            viewModel.getGamePlayNowUrl()?.let { playNowUrl ->
                openUrlInNewActivity(playNowUrl)
            }
        }
        binding.btnProfilePage.setOnClickListener {
            viewModel.getGameProfileUrl()?.let { profileUrl ->
                openUrlInNewActivity(profileUrl)
            }
        }
        binding.txtGameDescription.setOnClickListener {
            viewModel.reverseIsGameDescriptionTextViewCollapsed()
        }
    }

    private fun openUrlInNewActivity(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun subscribeCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.gameName.collect {
                        it?.let {
                            setGameNameToToolbar(it)
                        }
                    }
                }
                launch {
                    viewModel.isGameDescriptionTextViewCollapsed.collect {
                        handleDescriptionTextViewCollapseState(it)
                    }
                }
                launch {
                    viewModel.gameDetailViewState.collect {
                        handleGameDetailViewState(it)
                    }
                }

            }
        }
    }

    private fun setGameNameToToolbar(gameName: String) {
        binding.toolbarGameDetail.title = gameName
    }

    private fun handleDescriptionTextViewCollapseState(isCollapsed: Boolean) {
        textViewCollapsingAnimation?.onCollapseStateChanged(isCollapsed)
    }

    private fun handleGameDetailViewState(vs: GameDetailViewState) {
        vs.errorMessage?.let {
            //some errors happened
            binding.scrollMainGameDetail.setVisibilityToGone()
            binding.txtGameDetailError.setVisibilityToVisible()
            binding.txtGameDetailError.text = it
        }
        //handle loading
        binding.swipeRefreshGameDetail.isRefreshing = vs.isLoading
        //handle data
        vs.gameDetail?.let {
            binding.scrollMainGameDetail.setVisibilityToVisible()
            binding.txtGameDetailError.setVisibilityToGone()
            setGameDetailValuesToTextViews(it)
        }
    }

    private fun setGameDetailValuesToTextViews(gameDetail: GameDetail) {
        //set image
        Glide.with(binding.root)
            .load(gameDetail.thumbnail)
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.no_pictures)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imgGameThumbnail)
        //title value
        binding.toolbarGameDetail.title = gameDetail.title
        //about the game
        binding.txtGameDescriptionHeader.text = "${getString(R.string.about)} ${gameDetail.title}"
        textViewCollapsingAnimation?.updateWithNewText(gameDetail.description)
        //additional information section
        binding.gameTitleBox.titleText.text = getString(R.string.title)
        binding.gameTitleBox.subText.text = gameDetail.title

        binding.gameDeveloperBox.titleText.text = getString(R.string.developer)
        binding.gameDeveloperBox.subText.text = gameDetail.developer

        binding.gamePublisherBox.titleText.text = getString(R.string.publisher)
        binding.gamePublisherBox.subText.text = gameDetail.publisher

        binding.gameReleaseDateBox.titleText.text = getString(R.string.release_date)
        binding.gameReleaseDateBox.subText.text = gameDetail.releaseDate

        binding.gameGenreBox.titleText.text = getString(R.string.genre)
        binding.gameGenreBox.subText.text = gameDetail.genre

        binding.gamePlatformBox.titleText.text = getString(R.string.platform)
        binding.gamePlatformBox.subText.text = gameDetail.platform

        //Minimum System Requirements section
        val unknownText = getString(R.string.Unknown)

        binding.gameOsBox.titleText.text = getString(R.string.os)
        binding.gameOsBox.subText.text =
            gameDetail.minimumSystemRequirements.os ?: unknownText

        binding.gameMemoryBox.titleText.text = getString(R.string.memory)
        binding.gameMemoryBox.subText.text =
            gameDetail.minimumSystemRequirements.memory ?: unknownText

        binding.gameStorageBox.titleText.text = getString(R.string.storage)
        binding.gameStorageBox.subText.text =
            gameDetail.minimumSystemRequirements.storage ?: unknownText

        binding.gameProcessorBox.titleText.text = getString(R.string.processor)
        binding.gameProcessorBox.subText.text =
            gameDetail.minimumSystemRequirements.processor ?: unknownText

        binding.gameGraphicsBox.titleText.text = getString(R.string.graphics)
        binding.gameGraphicsBox.subText.text =
            gameDetail.minimumSystemRequirements.graphics ?: unknownText


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        subscribeCollectors()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        textViewCollapsingAnimation = null
        _binding = null
    }

    companion object {
        private const val DESCRIPTION_TEXTVIEW_COLLAPSED_MAX_LINE = 4
        private const val DESCRIPTION_TEXTVIEW_COLLAPSING_ANIMATION_DURATION = 1000L
    }

}