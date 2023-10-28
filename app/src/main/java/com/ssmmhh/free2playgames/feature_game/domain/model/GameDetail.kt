package com.ssmmhh.free2playgames.feature_game.domain.model

data class GameDetail(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val status: String,
    val shortDescription: String,
    val description: String,
    val gameUrl: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    val releaseDate: String,
    val freeToGameProfileUrl: String,
    val minimumSystemRequirements: SystemRequirements?,
    val screenshots: List<String>
)