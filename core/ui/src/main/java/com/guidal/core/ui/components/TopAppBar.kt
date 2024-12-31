package com.guidal.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guidal.core.ui.models.UiModelTopAppBarIcon
import com.guidal.core.ui.theme.GuidalIcons
import com.guidal.core.ui.theme.GuidalTheme

// TODO KDoc when finalized
// TODO Android test
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: UiModelTopAppBarIcon? = null,
    actions: List<UiModelTopAppBarIcon> = emptyList()
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        androidx.compose.material3.TopAppBar(

            // Required
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            },

            // Optional
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(horizontal = 4.dp),
            navigationIcon = {
                if (navigationIcon != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            enabled = navigationIcon.isEnabled,
                            onClick = { navigationIcon.onClick() },
                        ) {
                            Icon(
                                imageVector = navigationIcon.icon,
                                contentDescription = navigationIcon.contentDescription,
                                tint = navigationIcon.color,
                                modifier = Modifier.size(navigationIcon.size)
                            )
                        }
                    }
                }
            },
            actions = {
                actions.forEach { action ->
                    Row(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            enabled = action.isEnabled,
                            onClick = { action.onClick() }
                        ) {
                            Icon(
                                imageVector = action.icon,
                                contentDescription = action.contentDescription,
                                tint = action.color,
                                modifier = Modifier.size(action.size)
                            )
                        }
                    }
                }
            },

            // Constant
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceBright,
                scrolledContainerColor = MaterialTheme.colorScheme.surfaceBright,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        )

        // Divider to separate top app bar from content
        HorizontalDivider()
    }
}

@Preview
@Composable
private fun PreviewTopAppBar() {
    GuidalTheme {
        TopAppBar(
            title = "Log in",
            navigationIcon = UiModelTopAppBarIcon(
                icon = GuidalIcons.Default.Guidal,
                onClick = {},
                color = MaterialTheme.colorScheme.primary
            ),
            actions = listOf(
                UiModelTopAppBarIcon(
                    icon = GuidalIcons.Default.Search,
                    onClick = {},
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        )
    }
}