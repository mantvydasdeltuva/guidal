package com.guidal.core.ui.models

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Module :core:ui
 *
 * Represents a model for an item in the bottom app bar, including its label, icons, and enabled state.
 *
 * @param label The label text displayed for the bottom app bar item.
 * @param selectedIcon The `ImageVector` for the icon displayed when the item is selected.
 * @param unselectedIcon The `ImageVector` for the icon displayed when the item is unselected.
 * @param enabled A flag indicating whether the bottom app bar item is enabled or not (defaults to `true`).
 *
 * ‎
 *
 * **Best Practices**
 * - Use `selectedIcon` and `unselectedIcon` to provide visual feedback for the item’s state, aiding in user navigation.
 * - Set the `enabled` flag to control the interactivity of the item, disabling it when appropriate (e.g., when the item should not be interacted with).
 */
// TODO Unit test
data class UiModelBottomAppBarItem (
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val enabled: Boolean = true
)