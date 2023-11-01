package com.ssmmhh.free2playgames.featureGame.presentation.games

import com.ssmmhh.free2playgames.featureGame.domain.model.Game

sealed interface GameListUiState {

    data class HasGames(
        val gameList: List<Game>
    ) : GameListUiState

    data object Loading : GameListUiState
}
