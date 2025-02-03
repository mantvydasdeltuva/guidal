package com.guidal.core.ui.models

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp

data class UiModelInTextButtonIcon (
    val imageVector: ImageVector,
    val contentDescription: String? = null,
    val size: Dp? = null
)