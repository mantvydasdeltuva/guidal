package com.guidal.core.ui.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp

/**
 * Module :core:ui
 *
 * Represents a model for a menu button icon, including its appearance, accessibility description, and size properties.
 *
 * @param imageVector The `ImageVector` representing the icon to be displayed on the menu button.
 * @param contentDescription An optional string for the icon’s content description, used for accessibility purposes (defaults to `null`).
 * @param tint An optional `Color` to apply as a tint to the icon (defaults to `null`).
 * @param size An optional `Dp` value specifying the size of the icon (defaults to `null`).
 *
 * ‎
 *
 * **Best Practices**
 * - Use `tint` to customize the color of the icon, but ensure it maintains the overall design consistency of the app.
 * - Set `size` when the icon’s size should differ from the default, ensuring the icon fits within the desired UI space.
 */
// TODO Unit test
data class UiModelMenuButtonIcon (
    val imageVector: ImageVector,
    val contentDescription: String? = null,
    val tint: Color? = null,
    val size: Dp? = null
)