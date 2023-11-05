package com.ssmmhh.free2playgames.featureGame.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FilterChip(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        AssistChip(
            onClick = onClick,
            label = { Text(text = text, color = MaterialTheme.colorScheme.onSurface) },
            trailingIcon = {
                Icon(
                    tint = MaterialTheme.colorScheme.onSurface,
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "$text filter",
                    modifier = Modifier.size(AssistChipDefaults.IconSize)
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFilterChip() {
    FilterChip(
        "Sort",
        {}
    )
}
