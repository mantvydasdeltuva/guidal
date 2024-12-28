package com.guidal.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guidal.core.ui.R
import com.guidal.core.ui.models.UiModelBottomAppBarItem
import com.guidal.core.ui.theme.GuidalIcons
import com.guidal.core.ui.theme.GuidalTheme

// TODO KDoc when finalized
// TODO Android test
@Composable
fun BottomAppBar(
    items: List<UiModelBottomAppBarItem>,
    selectedItemIndex: Int,
    modifier: Modifier = Modifier,
    onItemClick: (index: Int) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Top border
        HorizontalDivider()
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surfaceBright,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = modifier
                .padding(top = 2.dp) // For top border
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = if (selectedItemIndex == index) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.label,
                            modifier = Modifier.size(dimensionResource(R.dimen.icon_size_24))
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = Color.Transparent,
                        disabledIconColor = MaterialTheme.colorScheme.primary,
                        disabledTextColor = MaterialTheme.colorScheme.primary
                    ),
                    label = {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    selected = selectedItemIndex == index,
                    enabled = item.enabled,
                    onClick = { onItemClick(index) }
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewBottomAppBar() {
    val index by remember { mutableIntStateOf(0) }

    val homeItem = UiModelBottomAppBarItem("Home", GuidalIcons.Default.Home, GuidalIcons.Outlined.Home)
    val discoverItem = UiModelBottomAppBarItem("Discover", GuidalIcons.Default.Discover, GuidalIcons.Outlined.Discover)
    val menuItem = UiModelBottomAppBarItem("Menu", GuidalIcons.Default.Menu, GuidalIcons.Outlined.Menu)

    val items = listOf(homeItem, discoverItem, menuItem)

    GuidalTheme {
        BottomAppBar (
            items = items,
            selectedItemIndex = index
        )
    }
}