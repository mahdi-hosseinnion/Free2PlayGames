package com.ssmmhh.free2playgames.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.ssmmhh.free2playgames.R

@Composable
fun Free2PlayGamesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val lightColors = lightColors(
        primary = colorResource(id = R.color.purple_500),
        primaryVariant = colorResource(id = R.color.purple_700),
        onPrimary = colorResource(id = R.color.white),
        secondary = colorResource(id = R.color.teal_200),
        onSecondary = colorResource(id = R.color.black),
    )
    val darkColors = darkColors(
        primary = colorResource(id = R.color.purple_500),
        primaryVariant = colorResource(id = R.color.purple_700),
        onPrimary = colorResource(id = R.color.white),
        secondary = colorResource(id = R.color.teal_200),
        onSecondary = colorResource(id = R.color.black),
    )
    val colors = if (darkTheme) darkColors else lightColors
    MaterialTheme(
        colors = colors,
        content = content
    )
}