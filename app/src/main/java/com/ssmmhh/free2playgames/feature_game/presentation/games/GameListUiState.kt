package com.ssmmhh.free2playgames.feature_game.presentation.games

import com.ssmmhh.free2playgames.feature_game.domain.model.Game

sealed interface GameListUiState {

    data class HasGames(
        val gameList: List<Game>
    ) : GameListUiState

    data object Loading : GameListUiState
}