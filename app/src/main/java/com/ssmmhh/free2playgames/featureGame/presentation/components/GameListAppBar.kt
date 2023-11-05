package com.ssmmhh.free2playgames.featureGame.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ssmmhh.free2playgames.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameListAppBar(
    modifier: Modifier = Modifier,
    onFilterClicked: () -> Unit,
    scrollContent: @Composable (innerPadding: PaddingValues) -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Surface(shadowElevation = 2.dp) {
                Column {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary, // TODO (Use priamry container)
                            titleContentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        title = {
                            Text(
                                text = stringResource(id = R.string.app_name),
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.titleLarge,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        },
                        scrollBehavior = scrollBehavior
                    )
                    FilterChip(
                        modifier = Modifier.padding(start = 12.dp),
                        text = stringResource(R.string.sort),
                        onClick = onFilterClicked
                    )
                }
            }
        }
    ) { innerPadding ->
        scrollContent(innerPadding)
    }
}
