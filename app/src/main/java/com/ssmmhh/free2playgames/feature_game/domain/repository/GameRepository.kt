package com.ssmmhh.free2playgames.feature_game.domain.repository

import com.ssmmhh.free2playgames.feature_game.data.util.Result
import com.ssmmhh.free2playgames.feature_game.domain.model.Game
import com.ssmmhh.free2playgames.feature_game.domain.model.GameDetail

interface GameRepository {

    suspend fun getGames(): Result<List<Game>>

    suspend fun getGameDetailById(id: Int): Result<GameDetail>
}