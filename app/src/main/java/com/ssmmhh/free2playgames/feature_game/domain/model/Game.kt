package com.ssmmhh.free2playgames.feature_game.domain.model

import java.util.UUID
import kotlin.random.Random

data class Game(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val shortDescription: String,
    val genre: String,
    val platform: String,
    val releaseDate: String,
)
fun TempGame(): Game = Game(
    id = Random.nextInt(),
    title = "CS:GO Counter strict ${UUID.randomUUID().toString().take(5)}",
    thumbnail = "https://static.digiato.com/digiato/2023/10/M45wF2YrAKDwdearegmHC5-910x600.jpg",
    shortDescription = "Microvolts: Recharged is a free-to-play lobby-based third-peay lobby-based third-person shooter developed and published by Masangsoft. The game features several toy-themed arenas. The arenas range from a small town to a back yard requiring players to climb tables and maneuver around tea cups.",
    genre = "Shooter",
    platform = "Window/Linux/PS/XBOX",
    releaseDate = "2/6/2018"
)