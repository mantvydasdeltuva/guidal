package com.guidal.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guidal.core.ui.R
import com.guidal.core.ui.models.UiModelMenuButtonIcon
import com.guidal.core.ui.theme.GuidalIcons
import com.guidal.core.ui.theme.GuidalTheme

// TODO KDoc when finalized
// TODO Android test
@Composable
fun MenuButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: UiModelMenuButtonIcon? = null,
    trailingIcon: UiModelMenuButtonIcon? = null,
    labelColor: Color? = null
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(
                if (interactionSource.collectIsPressedAsState().value && enabled)
                    MaterialTheme.colorScheme.surface
                else MaterialTheme.colorScheme.surfaceBright
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = { if (enabled) onClick() }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 28.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                Icon(
                    imageVector = it.imageVector,
                    contentDescription = it.contentDescription,
                    tint = it.tint ?: MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(it.size ?: dimensionResource(R.dimen.icon_size_24))
                )
            }

            Text(
                text = label,
                color = labelColor ?: MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .weight(1f)
            )

            trailingIcon?.let {
                Icon(
                    imageVector = it.imageVector,
                    contentDescription = it.contentDescription,
                    tint = it.tint ?: MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(it.size ?: dimensionResource(R.dimen.icon_size_24))
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewMenuButton() {
    GuidalTheme {
        MenuButton(
            label = "Profile",
            onClick = {},
            leadingIcon = UiModelMenuButtonIcon(
                imageVector = GuidalIcons.Outlined.Profile,
                size = dimensionResource(R.dimen.icon_size_16)
            ),
            trailingIcon = UiModelMenuButtonIcon(
                imageVector = GuidalIcons.Default.ChevronForward,
                size = dimensionResource(R.dimen.icon_size_12)
            )
        )
    }
}