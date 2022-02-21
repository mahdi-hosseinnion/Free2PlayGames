package com.ssmmhh.free2playgames.feature_game.presentation.game_detail.viewstate

import com.ssmmhh.free2playgames.feature_game.domain.model.GameDetail

data class GameDetailViewState(
    val isLoading: Boolean = false,
    val gameDetail: GameDetail? = null,
    val errorMessage: String? = null
)