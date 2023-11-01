package com.ssmmhh.free2playgames.featureGame.presentation.gameDetail.viewstate

import com.ssmmhh.free2playgames.featureGame.domain.model.GameDetail

data class GameDetailViewState(
    val isLoading: Boolean = false,
    val gameDetail: GameDetail? = null
)
