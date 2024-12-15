package com.guidal.core.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.guidal.core.ui.theme.GuidalTheme

// TODO KDoc when finalized
// TODO Android test
@Composable
fun Snackbar(
    snackbarData: SnackbarData
) {
    androidx.compose.material3.Snackbar(

        // Required
        snackbarData = snackbarData,

        // Constant
        dismissActionContentColor =  MaterialTheme.colorScheme.onSurfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.surfaceDim
    )
}

@Preview(showBackground = false)
@Composable
private fun PreviewSnackbar() {
    val snackbarVisual = object : SnackbarVisuals {
        override val actionLabel: String? = null
        override val duration: SnackbarDuration = SnackbarDuration.Short
        override val message: String = "Email is already taken"
        override val withDismissAction: Boolean = true
    }

    val snackbarData = object : SnackbarData {
        override val visuals: SnackbarVisuals = snackbarVisual
        override fun dismiss() {}
        override fun performAction() {}
    }

    GuidalTheme {
        Snackbar(
            snackbarData = snackbarData
        )
    }
}