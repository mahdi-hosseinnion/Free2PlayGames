package com.ssmmhh.free2playgames.featureGame.presentation.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssmmhh.free2playgames.R
import com.ssmmhh.free2playgames.featureGame.domain.model.GameSortOptions
import kotlinx.coroutines.launch

sealed class SortAndFilterEditor {
    data class Sort(
        val context: Context,
        val title: String = context.getString(R.string.sort),
        val options: List<GameSortOptions> = GameSortOptions.entries,
        val selectedOption: GameSortOptions,
        val onOptionSelected: (GameSortOptions) -> Unit
    ) : SortAndFilterEditor()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    filterOptions: SortAndFilterEditor?,
    onDismissRequest: () -> Unit
) {
    if (filterOptions == null) return
    if (filterOptions !is SortAndFilterEditor.Sort) return
    val scope = rememberCoroutineScope()
    val sheetState: SheetState = rememberModalBottomSheetState()

    fun hideBottomSheet() {
        scope.launch {
            sheetState.hide()
            onDismissRequest()
        }
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        SortOptionsSelector(
            allOptions = filterOptions.options,
            selectedOption = filterOptions.selectedOption,
            onSortOptionSelected = {
                filterOptions.onOptionSelected.invoke(it)
                hideBottomSheet()
            },
            onDismissRequest = {
                hideBottomSheet()
            }
        )
    }
}

@Composable
fun SortOptionsSelector(
    allOptions: List<GameSortOptions>,
    selectedOption: GameSortOptions,
    onSortOptionSelected: (GameSortOptions) -> Unit,
    onDismissRequest: () -> Unit
) {
    Column(
        modifier = Modifier.padding(
            start = 8.dp,
            top = 0.dp,
            end = 8.dp,
            bottom = 8.dp
        )
    ) {
        Text(
            text = stringResource(R.string.sorted_by_),
            modifier = Modifier.padding(
                start = 8.dp,
                top = 0.dp,
                end = 8.dp,
                bottom = 8.dp
            )
        )
        allOptions.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSortOptionSelected.invoke(option)
                    }
            ) {
                RadioButton(
                    selected = option == selectedOption,
                    onClick = { onSortOptionSelected.invoke(option) }
                )
                Text(
                    text = stringResource(id = option.textRes),
                    modifier = Modifier.align(alignment = Alignment.CenterVertically)
                )
            }
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    top = 16.dp,
                    end = 8.dp,
                    bottom = 24.dp
                ),
            onClick = onDismissRequest
        ) {
            Text(stringResource(R.string.cancel))
        }
    }
}

@Preview
@Composable
fun PreviewFilterBottomSheet() {
    Button(onClick = {}) {
        Text(text = "ClickToOpen")
    }
}
