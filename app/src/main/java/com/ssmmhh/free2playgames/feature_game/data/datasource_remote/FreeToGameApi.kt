package com.ssmmhh.free2playgames.feature_game.data.datasource_remote

import com.ssmmhh.free2playgames.feature_game.data.datasource_remote.dto.GameDto
import retrofit2.http.GET

interface FreeToGameApi {

    @GET("games")
    suspend fun getGames(): List<GameDto>
}