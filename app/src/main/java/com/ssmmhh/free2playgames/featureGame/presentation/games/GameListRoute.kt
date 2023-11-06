package com.ssmmhh.free2playgames.featureGame.presentation.games

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ssmmhh.free2playgames.featureGame.domain.model.Game
import com.ssmmhh.free2playgames.featureGame.domain.model.GameSortOptions

@Composable
fun GameListRoute(viewModel: GamesViewModel, onSelectGame: (Game) -> Unit) {
    val uiState by viewModel.gameListUiState.collectAsStateWithLifecycle()
    GameListRoute(
        uiState = uiState,
        onSelectGame = onSelectGame,
        onSortOptionChanged = { viewModel.changeSortOptionTo(it) },
        onRefreshGames = { viewModel.fetchGames() }
    )
}

@Composable
fun GameListRoute(
    uiState: GameListUiState,
    onSelectGame: (Game) -> Unit,
    onSortOptionChanged: (GameSortOptions) -> Unit,
    onRefreshGames: () -> Unit
) {
    when (uiState) {
        is GameListUiState.HasGames -> GameListScreen(
            games = uiState.gameList,
            onClickedOnGame = onSelectGame,
            selectedSortOption = uiState.sortOption,
            onSortOptionChanged = onSortOptionChanged
        )

        is GameListUiState.Failed -> GameErrorScreen(retry = onRefreshGames)
        GameListUiState.Loading -> GameLoadingScreen()
    }
}
