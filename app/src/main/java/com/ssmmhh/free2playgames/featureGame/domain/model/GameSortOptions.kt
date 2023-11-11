package com.ssmmhh.free2playgames.featureGame.domain.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ssmmhh.free2playgames.R

enum class GameSortOptions(
    @StringRes val textRes: Int
) {
    RELEASE_DATE(R.string.newest),
    POPULARITY(R.string.popularity),
    ALPHABETICAL(R.string.alphabetical),
    RELEVANCE(R.string.relevance);

    companion object
}

val GameSortOptions.Companion.DEFAULT: GameSortOptions
    get() = GameSortOptions.RELEVANCE

val GameSortOptions.text: String
    @Composable
    get() = stringResource(id = this.textRes)