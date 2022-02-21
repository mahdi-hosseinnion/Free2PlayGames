package com.ssmmhh.free2playgames.feature_game.data.datasource_remote

import com.ssmmhh.free2playgames.feature_game.data.datasource_remote.dto.GameDetailDto
import com.ssmmhh.free2playgames.feature_game.data.datasource_remote.dto.GameDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FreeToGameApi {

    @GET("games")
    suspend fun getGames(): List<GameDto>

    @GET("game")
    suspend fun getGameDetailById(
        @Query("id") id: Int
    ): GameDetailDto
}