package com.ssmmhh.free2playgames.featureGame.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.ssmmhh.free2playgames.featureGame.domain.model.Game

data class GameDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("short_description")
    val shortDescription: String,
    @SerializedName("game_url")
    val gameUrl: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("platform")
    val platform: String,
    @SerializedName("publisher")
    val publisher: String,
    @SerializedName("developer")
    val developer: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("freetogame_profile_url")
    val freeToGameProfileUrl: String
)

fun GameDto.toGame(): Game =
    Game(
        id = this.id,
        title = this.title,
        thumbnail = this.thumbnail,
        shortDescription = this.shortDescription,
        genre = this.genre,
        platform = this.platform,
        releaseDate = this.releaseDate
    )

fun List<GameDto>.toGameList(): List<Game> = this.map { it.toGame() }
