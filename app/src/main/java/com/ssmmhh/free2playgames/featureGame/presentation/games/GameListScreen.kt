package com.ssmmhh.free2playgames.featureGame.presentation.games

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ssmmhh.free2playgames.R
import com.ssmmhh.free2playgames.featureGame.domain.model.Game
import com.ssmmhh.free2playgames.featureGame.domain.model.TempGame

@Composable
fun GameListScreen(games: List<Game>, onClickedOnGame: (id: Game) -> Unit) {
    LazyColumn {
        items(items = games, key = { it.id }) {
            GameItem(game = it, onClickedOnGame = onClickedOnGame)
        }
    }
}

@Composable
fun GameItem(game: Game, onClickedOnGame: (id: Game) -> Unit, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_medium)),
        elevation = 2.dp,
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_large))
            .clickable {
                onClickedOnGame.invoke(game)
            }
    ) {
        Column {
            AsyncImage(
                model = game.thumbnail,
                modifier = Modifier.aspectRatio(1.77F),
                contentDescription = stringResource(R.string.game_thumbnail_image_description),
                onState = {
                }
            )
            val sp = dimensionResource(id = R.dimen.padding_small)
            Text(
                text = game.title,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(sp),
                maxLines = 1
            )
            Text(
                text = game.shortDescription,
                maxLines = 2,
                modifier = Modifier.padding(start = sp, top = 0.dp, end = sp, bottom = sp),
                style = MaterialTheme.typography.body1,
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

@Preview(widthDp = 90, heightDp = 160)
@Composable
fun GameLoadingScreen() {
    Surface(Modifier.fillMaxSize()) {
        Box {
            CircularProgressIndicator(
                Modifier.align(Alignment.Center)
            )
        }
    }
}
