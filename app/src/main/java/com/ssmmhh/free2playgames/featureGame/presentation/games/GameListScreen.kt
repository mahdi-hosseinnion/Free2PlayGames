package com.ssmmhh.free2playgames.featureGame.presentation.games

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssmmhh.free2playgames.R
import com.ssmmhh.free2playgames.featureGame.domain.model.Game
import com.ssmmhh.free2playgames.featureGame.presentation.components.GameItem
import com.ssmmhh.free2playgames.featureGame.presentation.components.GameListAppBar

@Composable
fun GameListScreen(games: List<Game>, onClickedOnGame: (id: Game) -> Unit) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column {
            GameListAppBar() { padding ->
                LazyColumn(contentPadding = padding) {
                    items(items = games, key = { it.id }) {
                        GameItem(game = it, onClickedOnGame = onClickedOnGame)
                    }
                }
            }
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
                    style = MaterialTheme.typography.bodyMedium
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
        message = stringResource(R.string.network_error_message)
    ) {}
}
