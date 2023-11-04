package com.ssmmhh.free2playgames.featureGame.presentation.games

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ssmmhh.free2playgames.R
import com.ssmmhh.free2playgames.featureGame.domain.model.Game
import com.ssmmhh.free2playgames.featureGame.domain.model.TempGame
import com.ssmmhh.free2playgames.theme.outlineColor

@Composable
fun GameListScreen(games: List<Game>, onClickedOnGame: (id: Game) -> Unit) {
    Surface(color = MaterialTheme.colors.background) {
        Column {
            TopAppBar() { padding ->
                LazyColumn(contentPadding = padding) {
                    items(items = games, key = { it.id }) {
                        GameItem(game = it, onClickedOnGame = onClickedOnGame)
                    }
                }
            }
        }
    }
}

@Composable
fun GameItem(game: Game, onClickedOnGame: (id: Game) -> Unit, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, outlineColor),
        elevation = 2.dp,
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
                style = MaterialTheme.typography.h5,
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
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
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

@Composable
fun GameErrorScreen(
    message: String = stringResource(R.string.network_error_message),
    retry: () -> Unit
) {
    Surface(Modifier.fillMaxSize()) {
        Box {
            Column(Modifier.align(Alignment.Center)) {
                Text(
                    text = message,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1
                )
                Spacer(
                    modifier = Modifier.size(8.dp)
                )
                Button(
                    onClick = retry,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = stringResource(R.string.retry))
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewGameErrorScreen() {
    GameErrorScreen(
        message = stringResource(R.string.network_error_message),
        {}
    )
}

@Preview
@Composable
private fun PreviewGameItem() {
    val game = TempGame()
    GameItem(game = game, {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    scrollContent: @Composable (innerPadding: PaddingValues) -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Surface(elevation = 2.dp) {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colors.primary, // TODO (Use priamry container)
                        titleContentColor = MaterialTheme.colors.onPrimary
                    ),
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.h6,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        }
    ) { innerPadding ->
        scrollContent(innerPadding)
    }
}
