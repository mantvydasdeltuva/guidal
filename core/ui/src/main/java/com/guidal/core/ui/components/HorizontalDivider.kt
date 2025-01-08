package com.guidal.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guidal.core.ui.theme.GuidalTheme

// TODO KDoc when finalized
// TODO Android test
@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.HorizontalDivider(

        // Optional
        modifier = modifier
            .fillMaxWidth(),

        // Constant
        color = MaterialTheme.colorScheme.surfaceDim,
        thickness = 1.dp
    )
}

@Preview(showBackground = false)
@Composable
private fun PreviewHorizontalDivider() {
    GuidalTheme {
        HorizontalDivider()
    }
}