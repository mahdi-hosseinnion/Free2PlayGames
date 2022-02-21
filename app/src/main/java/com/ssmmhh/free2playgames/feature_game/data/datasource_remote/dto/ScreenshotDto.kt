package com.ssmmhh.free2playgames.feature_game.data.datasource_remote.dto


import com.google.gson.annotations.SerializedName

data class ScreenshotDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String
)