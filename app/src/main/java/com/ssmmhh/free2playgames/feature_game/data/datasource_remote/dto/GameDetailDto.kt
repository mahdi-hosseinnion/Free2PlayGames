package com.ssmmhh.free2playgames.feature_game.data.datasource_remote.dto


import com.google.gson.annotations.SerializedName
import com.ssmmhh.free2playgames.feature_game.domain.model.GameDetail

data class GameDetailDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("short_description")
    val shortDescription: String,
    @SerializedName("description")
    val description: String,
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
    val freeToGameProfileUrl: String,
    @SerializedName("minimum_system_requirements")
    val minimumSystemRequirements: SystemRequirementsDto?,
    @SerializedName("screenshots")
    val screenshots: List<ScreenshotDto>
)

fun GameDetailDto.toGameDetail(): GameDetail = GameDetail(
    id = this.id,
    title = this.title,
    thumbnail = this.thumbnail,
    status = this.status,
    shortDescription = this.shortDescription,
    description = this.description,
    gameUrl = this.gameUrl,
    genre = this.genre,
    platform = this.platform,
    publisher = this.publisher,
    developer = this.developer,
    releaseDate = this.releaseDate,
    freeToGameProfileUrl = this.freeToGameProfileUrl,
    minimumSystemRequirements = this.minimumSystemRequirements?.toSystemRequirements(),
    screenshots = this.screenshots.map { it.image },
)