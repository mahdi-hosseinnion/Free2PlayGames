package com.ssmmhh.free2playgames.feature_game.data.datasource_remote.dto


import com.google.gson.annotations.SerializedName
import com.ssmmhh.free2playgames.feature_game.domain.model.SystemRequirements

data class SystemRequirementsDto(
    @SerializedName("os")
    val os: String?,
    @SerializedName("processor")
    val processor: String?,
    @SerializedName("memory")
    val memory: String?,
    @SerializedName("graphics")
    val graphics: String?,
    @SerializedName("storage")
    val storage: String?
)

fun SystemRequirementsDto.toSystemRequirements(): SystemRequirements = SystemRequirements(
    os = this.os,
    processor = this.processor,
    memory = this.memory,
    graphics = this.graphics,
    storage = this.storage,
)