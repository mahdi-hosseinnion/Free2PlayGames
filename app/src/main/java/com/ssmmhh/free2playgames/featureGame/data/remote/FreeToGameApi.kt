package com.ssmmhh.free2playgames.featureGame.data.remote

import com.ssmmhh.free2playgames.featureGame.data.remote.dto.GameDetailDto
import com.ssmmhh.free2playgames.featureGame.data.remote.dto.GameDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FreeToGameApi {

    @GET("games")
    suspend fun getGames(@Query("sort-by") sort: String?): List<GameDto>

    @GET("game")
    suspend fun getGameDetailById(
        @Query("id") id: Int
    ): GameDetailDto
}
