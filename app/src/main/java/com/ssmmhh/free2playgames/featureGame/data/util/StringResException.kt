package com.ssmmhh.free2playgames.featureGame.data.util

import androidx.annotation.StringRes

data class StringResException(
    @StringRes val stringRes: Int
) : Exception()

fun Exception.takeStringRes(@StringRes default: Int): Int =
    if (this is StringResException) this.stringRes else default
