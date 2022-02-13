package com.ssmmhh.free2playgames.feature_game.domain.repository

import com.ssmmhh.free2playgames.feature_game.domain.model.Game

interface GameRepository {

    suspend fun getGames(): List<Game>
}