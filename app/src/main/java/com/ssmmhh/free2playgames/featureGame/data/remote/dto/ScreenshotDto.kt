package com.ssmmhh.free2playgames.featureGame.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ScreenshotDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String
)
