package com.ssmmhh.free2playgames.feature_game.presentation.games

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ssmmhh.free2playgames.R
import com.ssmmhh.free2playgames.feature_game.domain.model.Game


@Composable
fun GameListScreen(viewModel: GamesViewModel, onClickedOnGame: (id: Game) -> Unit) {
    val viewState = viewModel.gameListViewState.collectAsState()
    GameList(viewState.value.games, onClickedOnGame)
}


@Composable
fun GameList(games: List<Game>, onClickedOnGame: (id: Game) -> Unit) {
    LazyColumn {
        items(games) {
            GameItem(game = it, onClickedOnGame = onClickedOnGame)
        }
    }
}

@Composable
fun GameItem(game: Game, onClickedOnGame: (id: Game) -> Unit) {
    Surface(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_medium)),
        elevation = 2.dp,
        modifier = Modifier
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
                    Log.d("GameListScreen", "AsyncImageonState: $it")
                }
            )
            val sp = dimensionResource(id = R.dimen.padding_small)
            Text(
                text = game.title,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(sp),
                maxLines = 1,
            )
            Text(
                text = game.shortDescription,
                minLines = 2,
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
    val game = createFakeGame()
    GameItem(game = game) {}
}

private fun createFakeGame(): Game = Game(
    id = 2722,
    title = "CS:GO Counter strict",
    thumbnail = "https://static.digiato.com/digiato/2023/10/M45wF2YrAKDwdearegmHC5-910x600.jpg",
    shortDescription = "Microvolts: Recharged is a free-to-play lobby-based third-peay lobby-based third-person shooter developed and published by Masangsoft. The game features several toy-themed arenas. The arenas range from a small town to a back yard requiring players to climb tables and maneuver around tea cups.",
    genre = "Shooter",
    platform = "Window/Linux/PS/XBOX",
    releaseDate = "2/6/2018"
)