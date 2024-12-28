package com.guidal.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guidal.core.ui.models.UiModelMenuButtonIcon
import com.guidal.core.ui.theme.GuidalIcons
import com.guidal.core.ui.theme.GuidalTheme

@Composable
fun HomeNavigationButton(
    label: String,
    type: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    sectionIcon: UiModelMenuButtonIcon? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .width(150.dp)
            .clickable(interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = { if (enabled) onClick() })
            .background(
                MaterialTheme.colorScheme.surfaceBright
            )
            .padding(all = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        sectionIcon?.let {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(
                        if (interactionSource.collectIsPressedAsState().value && enabled) MaterialTheme.colorScheme.surface
                        else MaterialTheme.colorScheme.surfaceBright,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = it.imageVector,
                    contentDescription = it.contentDescription,
                    tint = it.tint ?: MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(dimensionResource(com.guidal.core.ui.R.dimen.icon_size_40))
                )
            }
        }

        // Label
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 8.dp) // Adjust spacing between icon and label
        )

        // Type
        Text(
            text = type,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewHomeNavigationButton() {
    GuidalTheme {
        HomeNavigationButton(
            label = "Transportation",
            type = "Post",
            onClick = {},
            sectionIcon = UiModelMenuButtonIcon(
                imageVector = GuidalIcons.Default.Commute
            ),
        )
    }
}