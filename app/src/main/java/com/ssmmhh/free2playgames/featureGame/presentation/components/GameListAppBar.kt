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
import com.ssmmhh.free2playgames.featureGame.domain.model.GameSortOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameListAppBar(
    modifier: Modifier = Modifier,
    onFilterClicked: () -> Unit,
    selectedSortOption: GameSortOptions,
    scrollContent: @Composable (innerPadding: PaddingValues) -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Surface(shadowElevation = 2.dp, color = MaterialTheme.colorScheme.primaryContainer) {
                Column {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer, // TODO (Use priamry container)
                            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        title = {
                            Text(
                                text = stringResource(id = R.string.app_name),
                                style = MaterialTheme.typography.titleLarge,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        },
                        scrollBehavior = scrollBehavior
                    )
                    FilterChip(
                        modifier = Modifier.padding(start = 12.dp, top = 2.dp, bottom = 2.dp),
                        text = stringResource(
                            R.string.sorted_by,
                            stringResource(selectedSortOption.textRes)
                        ),
                        onClick = onFilterClicked
                    )
                }
            }
        }
    ) { innerPadding ->
        scrollContent(innerPadding)
    }
}
