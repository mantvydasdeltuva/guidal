package com.guidal.feature.home.main

import com.guidal.core.ui.models.UiModelMenuButtonIcon

data class HomeNavigationButtonModel(
    val label: String,
    val type: String,
    val onClick: () -> Unit,
    val enabled: Boolean = true,
    val sectionIcon: UiModelMenuButtonIcon? = null
)