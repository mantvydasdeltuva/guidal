package com.guidal.core.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.guidal.core.ui.theme.GuidalTheme

@Composable
fun Switch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    androidx.compose.material3.Switch(

        // Required
        checked = checked,
        onCheckedChange = {
            onCheckedChange(it)
        },

        // Optional
        modifier = modifier,
        enabled = enabled,

        // Constant
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.primary,
            checkedTrackColor = Color.Transparent,
            checkedBorderColor = MaterialTheme.colorScheme.primary,
            uncheckedTrackColor = Color.Transparent,
            uncheckedBorderColor = MaterialTheme.colorScheme.onSurface,
            uncheckedThumbColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Preview(showBackground = false)
@Composable
private fun PreviewSwitch() {
    GuidalTheme {
        Switch(
            checked = true,
            onCheckedChange = {}
        )
    }
}