package com.ssmmhh.free2playgames.feature_game.presentation.games

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssmmhh.free2playgames.feature_game.domain.model.Game


@Composable
fun GameListRoute(viewModel: GamesViewModel, onSelectGame: (Game) -> Unit) {
    val uiState by viewModel.gameListUiState.collectAsStateWithLifecycle()
    GameListRoute(uiState, onSelectGame)
}

@Composable
fun GameListRoute(uiState: GameListUiState, onSelectGame: (Game) -> Unit) {
    when (uiState) {
        is GameListUiState.HasGames -> GameListScreen(uiState.gameList, onSelectGame)
        GameListUiState.Loading -> GameLoadingScreen()
    }

}