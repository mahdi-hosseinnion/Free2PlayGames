package com.ssmmhh.free2playgames.feature_game.presentation.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssmmhh.free2playgames.R
import com.ssmmhh.free2playgames.common.processQueue
import com.ssmmhh.free2playgames.databinding.FragmentGamesBinding
import com.ssmmhh.free2playgames.feature_game.domain.model.Game
import com.ssmmhh.free2playgames.feature_game.presentation.games.viewstate.GameListViewState
import com.ssmmhh.free2playgames.feature_game.presentation.util.setVisibilityToVisible
import com.ssmmhh.free2playgames.theme.Free2PlayGamesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GamesFragment : Fragment() {

    private val gamesRecyclerViewAdapter = GameListRecyclerViewAdapter() { item, _ ->

    }

    private val viewModel by viewModels<GamesViewModel>()

    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!


    private fun setupUI() {
        //reset back to right action bar value after navigate back from detail
        setupRecyclerView()
        setupSwipeToRefreshLayout()
    }

    private fun setupRecyclerView() {
        binding.recyclerGameList.apply {
            layoutManager = LinearLayoutManager(this@GamesFragment.requireContext())
            adapter = gamesRecyclerViewAdapter
        }
    }

    private fun setupSwipeToRefreshLayout() {
        binding.swipeRefreshGameList.setOnRefreshListener {
            viewModel.refreshGames()
        }

    }

    private fun subscribeCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.gameListViewState.collect {
                        handleRecyclerViewViewState(it)
                    }
                }
                launch {
                    viewModel.stateMessageQueue.collect {
                        processQueue(
                            this@GamesFragment.requireContext(),
                            it,
                        ) {
                            viewModel.removeHeadFromQueue()
                        }
                    }
                }

            }
        }
    }

    private fun handleRecyclerViewViewState(vs: GameListViewState) {
        if (vs.games.isNotEmpty()) {
            binding.recyclerGameList.setVisibilityToVisible()
            gamesRecyclerViewAdapter.submitList(vs.games)
        }
        //handle loading
        binding.swipeRefreshGameList.isRefreshing = vs.isLoading

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        subscribeCollectors()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        return ComposeView(requireContext()).apply {
            setContent {
                Free2PlayGamesTheme {
                    GameListScreen(viewModel) { game ->
                        navigateToDetailScreen(game)
                    }
                }
            }
        }
    }

    /*
     * Listen for option item selections so that we receive a notification
     * when the user requests a refresh by selecting the refresh action bar item.
     * This is for users with accessibility needs so they could manually update game list too.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Check if user triggered a refresh:
            R.id.menu_refresh -> {
                // Signal SwipeRefreshLayout to start the progress indicator
                binding.swipeRefreshGameList.isRefreshing = true
                // Start the refresh background task.
                viewModel.refreshGames()

                return true
            }
        }
        // User didn't trigger a refresh, let the superclass handle this action
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToDetailScreen(game: Game) {
        //on click on recycler item
        val action = GamesFragmentDirections.actionGamesFragmentToGameDetailFragment(
            gameId = game.id,
            gameTitle = game.title
        )
        findNavController().navigate(action)
    }
}