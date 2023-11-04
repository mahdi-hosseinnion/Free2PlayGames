package com.ssmmhh.free2playgames.featureGame.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ssmmhh.free2playgames.theme.icons.ArrowsSort
import com.ssmmhh.free2playgames.theme.icons.F2PGIcons
import kotlin.Boolean as Boolean

@Composable
fun FilterChip(
    modifier: Modifier = Modifier,
    filterOptions: List<String>,
    selectedFilterIndex: Int,
    onFilterSelected: (Int) -> Unit
) {
    var isDropDownExpanded by rememberSaveable { mutableStateOf<Boolean>(false) }
    Column(modifier = modifier) {
        AssistChip(
            onClick = { isDropDownExpanded = !isDropDownExpanded },
            label = { Text(filterOptions[selectedFilterIndex]) },
            leadingIcon = {
                Icon(
                    imageVector = F2PGIcons.ArrowsSort,
                    contentDescription = "Sort options",
                    modifier = Modifier.size(AssistChipDefaults.IconSize)
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Sort options",
                    modifier = Modifier.size(AssistChipDefaults.IconSize)
                )
            }
        )
        FilterDropDown(
            filterOptions = filterOptions,
            expanded = isDropDownExpanded,
            onDismissRequest = {
                isDropDownExpanded = false
            },
            onFilterSelected = { index ->
                isDropDownExpanded = false
                onFilterSelected(index)
            }
        )
    }
}

@Composable
fun FilterDropDown(
    filterOptions: List<String>,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onFilterSelected: (Int) -> Unit
) {
    DropdownMenu(expanded = expanded, onDismissRequest = onDismissRequest) {
        filterOptions.forEachIndexed { index, filter ->
            DropdownMenuItem(
                text = {
                    Text(text = filter)
                },
                onClick = {
                    onFilterSelected(index)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFilterChip() {
    FilterChip(
        filterOptions = listOf("release-date", "popularity", "alphabetical", "relevance"),
        selectedFilterIndex = 2,
        onFilterSelected = {}
    )
}
