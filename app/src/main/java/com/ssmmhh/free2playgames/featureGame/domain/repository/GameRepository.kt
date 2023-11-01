package com.ssmmhh.free2playgames.featureGame.domain.repository

import com.ssmmhh.free2playgames.featureGame.data.util.Result
import com.ssmmhh.free2playgames.featureGame.domain.model.Game
import com.ssmmhh.free2playgames.featureGame.domain.model.GameDetail

interface GameRepository {

    suspend fun getGames(): Result<List<Game>>

    suspend fun getGameDetailById(id: Int): Result<GameDetail>
}
