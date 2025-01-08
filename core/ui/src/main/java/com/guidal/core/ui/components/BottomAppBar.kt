package com.guidal.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalDivider()
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surfaceBright,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = modifier
                .padding(top = 1.dp)
                .height(65.dp)

        ) {
            items.forEachIndexed { index, item ->
                val interactionSource = remember { MutableInteractionSource() }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = {
                                if (index != selectedItemIndex) {
                                    onItemClick(index)
                                }
                            }
                        )
                        .background(Color.Transparent)
                        .padding(vertical = 5.dp)
                ) {
                    Icon(
                        imageVector = if (selectedItemIndex == index)
                            item.selectedIcon
                        else
                            item.unselectedIcon,
                        contentDescription = item.label,
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.icon_size_20)),
                        tint = if (selectedItemIndex == index)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(top = 5.dp),
                        color = if (selectedItemIndex == index)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
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