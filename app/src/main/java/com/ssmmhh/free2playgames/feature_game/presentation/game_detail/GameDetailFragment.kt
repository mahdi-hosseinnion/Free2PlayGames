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
import androidx.viewpager.widget.ViewPager
import com.ssmmhh.free2playgames.R
import com.ssmmhh.free2playgames.common.processQueue
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

    private lateinit var imagesViewPagerAdapter: GameDetailImagesViewPagerAdapter

    private fun setupUi() {
        setupSwipeToRefreshLayout()
        setupOnclickListeners()
        setupTextViewCollapsingAnimation()
        instantiateImagesViewPager()
        //set on click listener on toolbar back button
        binding.toolbarGameDetail.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    private fun instantiateImagesViewPager() {
        imagesViewPagerAdapter = GameDetailImagesViewPagerAdapter()
        binding.vpgGameImages.adapter = imagesViewPagerAdapter

        binding.vpgGameImages.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                viewModel.onImageSliderPositionChanged(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                /** Disable swipe to refresh while user is scrolling the view pager*/
                enableDisableSwipeRefresh(state == ViewPager.SCROLL_STATE_IDLE)
            }
        })

    }

    private fun enableDisableSwipeRefresh(enable: Boolean) {
        binding.swipeRefreshGameDetail.isEnabled = enable
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
                launch {
                    viewModel.stateMessageQueue.collect {
                        processQueue(
                            this@GameDetailFragment.requireContext(),
                            it,
                        ) {
                            viewModel.removeHeadFromQueue()
                        }
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
        imagesViewPagerAdapter.submitNewData(buildList {
            this.add(gameDetail.thumbnail)
            this.addAll(gameDetail.screenshots)
        })
        //Restore image position after rotation
        viewModel.getImageSliderPosition().let {
            val countOfImages = gameDetail.screenshots.size.plus(1)
            if (it < countOfImages) {
                binding.vpgGameImages.setCurrentItem(it, false)
            }
        }
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