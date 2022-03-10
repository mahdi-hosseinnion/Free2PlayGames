package com.ssmmhh.free2playgames.feature_game.presentation.games.viewstate

import com.ssmmhh.free2playgames.feature_game.domain.model.Game

data class GameListViewState(
    val isLoading: Boolean = false,
    val games: List<Game> = emptyList(),
)
