package com.ssmmhh.free2playgames.feature_game.domain.model

data class Game(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val shortDescription: String,
    val genre: String,
    val platform: String,
    val releaseDate: String,
)