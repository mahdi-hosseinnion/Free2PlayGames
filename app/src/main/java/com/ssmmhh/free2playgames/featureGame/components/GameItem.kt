package com.ssmmhh.free2playgames.featureGame.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ssmmhh.free2playgames.R
import com.ssmmhh.free2playgames.featureGame.domain.model.Game
import com.ssmmhh.free2playgames.featureGame.domain.model.TempGame
import com.ssmmhh.free2playgames.theme.outlineColor

@Composable
fun GameItem(game: Game, onClickedOnGame: (id: Game) -> Unit, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, outlineColor),
        shadowElevation = 2.dp,
        modifier = modifier
            .padding(12.dp)
            .clickable {
                onClickedOnGame.invoke(game)
            }
    ) {
        Column {
            AsyncImage(
                model = game.thumbnail,
                modifier = Modifier
                    .padding(2.dp)
                    .aspectRatio(1.77F)
                    .clip(
                        RoundedCornerShape(14.dp)
                    ),
                filterQuality = FilterQuality.Medium,
                contentDescription = stringResource(R.string.game_thumbnail_image_description),
                onState = {
                }
            )
            val padding = 8.dp
            Text(
                text = game.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(
                    start = padding,
                    top = padding / 2,
                    end = padding,
                    bottom = padding
                ),
                maxLines = 1
            )
            Text(
                text = game.shortDescription,
                maxLines = 2,
                modifier = Modifier.padding(
                    start = padding,
                    top = 0.dp,
                    end = padding,
                    bottom = padding * 1.5f
                ),
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun PreviewGameItem() {
    val game = TempGame()
    GameItem(game = game, {})
}
