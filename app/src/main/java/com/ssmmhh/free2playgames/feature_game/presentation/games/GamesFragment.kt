package com.ssmmhh.free2playgames.feature_game.presentation.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssmmhh.free2playgames.feature_game.domain.model.Game
import com.ssmmhh.free2playgames.theme.Free2PlayGamesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamesFragment : Fragment() {

    private val viewModel by viewModels<GamesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Free2PlayGamesTheme {
                    GameListRoute(viewModel) { game ->
                        navigateToDetailScreen(game)
                    }
                }
            }
        }
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