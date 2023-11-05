package com.ssmmhh.free2playgames.featureGame.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ssmmhh.free2playgames.featureGame.domain.model.GameSortOptions
import com.ssmmhh.free2playgames.featureGame.domain.model.text

sealed class FilterOptions(
    val options: @Composable () -> List<String>
) {
    data object Sort : FilterOptions({ GameSortOptions.entries.map { it.text } })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    filterOptions: FilterOptions?,
    onDismissRequest: () -> Unit
) {
    val sheetState: SheetState = rememberModalBottomSheetState()
    if (filterOptions != null) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onDismissRequest
        ) {
            Column {
                filterOptions.options().forEach {
                    Button(
                        onClick = {
//                        Toast.makeText(LocalContext.current, "", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Text(text = it)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewFilterBottomSheet() {
    Button(onClick = {}) {
        Text(text = "ClickToOpen")
    }
//    FilterBottomSheet(true, listOf("file", "edit", "view", "navigate", "code"))
}
