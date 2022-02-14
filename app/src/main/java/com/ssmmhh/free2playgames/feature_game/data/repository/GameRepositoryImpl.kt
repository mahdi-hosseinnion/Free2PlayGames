package com.ssmmhh.free2playgames.feature_game.data.repository

import com.ssmmhh.free2playgames.feature_game.data.datasource_remote.FreeToGameApi
import com.ssmmhh.free2playgames.feature_game.data.datasource_remote.dto.toGameList
import com.ssmmhh.free2playgames.feature_game.data.util.Result
import com.ssmmhh.free2playgames.feature_game.data.util.safeApiCall
import com.ssmmhh.free2playgames.feature_game.domain.model.Game
import com.ssmmhh.free2playgames.feature_game.domain.repository.GameRepository
import javax.inject.Inject

class GameRepositoryImpl
@Inject
constructor(
    private val freeToGameApi: FreeToGameApi
) : GameRepository {

    override suspend fun getGames(): Result<List<Game>> = safeApiCall {
        freeToGameApi.getGames().toGameList()
    }

}