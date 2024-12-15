package com.guidal.core.ui.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Module :core:ui
 *
 * Represents a model for an icon in the top app bar, including its appearance, behavior, and accessibility information.
 *
 * @param icon The `ImageVector` representing the icon to be displayed.
 * @param onClick The lambda function to be executed when the icon is clicked.
 * @param color The color of the icon.
 * @param isEnabled A flag indicating whether the icon is enabled or not (defaults to `true`).
 * @param contentDescription A string describing the icon's content for accessibility purposes (defaults to an empty string).
 * @param size The size of the icon, defined in density-independent pixels (dp) (defaults to 24.dp).
 *
 * ‎
 *
 * **Best Practices**
 * - Set the `contentDescription` for accessibility purposes to provide a meaningful description of the icon for screen readers.
 * - Use `isEnabled` to control whether the icon should be interactive or visually disabled.
 */
data class UiModelTopAppBarIcon (
    val icon: ImageVector,
    val onClick: () -> Unit,
    val color: Color,
    val isEnabled: Boolean = true,
    val contentDescription: String = "",
    val size: Dp = 24.dp
)