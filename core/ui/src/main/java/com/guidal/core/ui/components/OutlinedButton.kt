package com.guidal.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guidal.core.ui.R
import com.guidal.core.ui.theme.GuidalColors
import com.guidal.core.ui.theme.GuidalTheme

// TODO KDoc when finalized
// TODO Android test
@Composable
fun OutlinedButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false
) {
    androidx.compose.material3.OutlinedButton(

        // Required
        onClick = { onClick() },

        // Optional
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = isEnabled && !isLoading,

        // Constant
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceBright,
            disabledContainerColor= MaterialTheme.colorScheme.surfaceBright,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.icon_size_24)),
                color = GuidalColors.Dark.onSurface
            )
        } else {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewOutlinedButton() {
    GuidalTheme {
        OutlinedButton(
            label = "Log in",
            onClick = {}
        )
    }
}
