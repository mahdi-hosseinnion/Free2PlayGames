package com.ssmmhh.free2playgames.featureGame.presentation.games

import com.ssmmhh.free2playgames.featureGame.domain.model.Game
import com.ssmmhh.free2playgames.featureGame.domain.model.GameSortOptions
import com.ssmmhh.free2playgames.featureGame.domain.model.NetworkError

sealed interface GameListUiState {

    data class HasGames(
        val gameList: List<Game>,
        val sortOption: GameSortOptions
    ) : GameListUiState

    data object Loading : GameListUiState

    data class Failed(
        val error: NetworkError?
    ) : GameListUiState
}
