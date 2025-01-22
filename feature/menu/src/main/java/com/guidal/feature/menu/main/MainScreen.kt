package com.guidal.feature.menu.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.guidal.feature.menu.R
import com.guidal.core.ui.components.MenuButton
import com.guidal.core.ui.components.Scaffold
import com.guidal.core.ui.components.TopAppBar
import com.guidal.core.ui.models.UiModelMenuButtonIcon
import com.guidal.core.ui.models.UiModelTopAppBarIcon
import com.guidal.core.ui.theme.GuidalIcons

@Composable
fun MainScreen(
    toProfile: () -> Unit,
    toSettings: () -> Unit,
    toPrivacy: () -> Unit,
    toAbout: () -> Unit,
    toSupport: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mainViewModel: MainViewModel = viewModel()
    val uiState by mainViewModel.uiState.collectAsState()

    // Component related
    val scrollState = rememberScrollState()

    // Ensures initial state when coming back with navigation stack
    LaunchedEffect(Unit) {
        mainViewModel.resetState()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                // TODO: Fix me! Use UserModel in uiState and DataStoreUtils in viewModel
//                title = "Welcome, ${user.value?.name}${user.value?.surname?.takeIf { it.isNotEmpty() }?.let { " $it" } ?: ""}!",
                title = stringResource(R.string.main_menu_title),
                navigationIcon = UiModelTopAppBarIcon(
                    icon = GuidalIcons.Default.Guidal,
                    onClick = { },
                    color = MaterialTheme.colorScheme.primary,
                    isEnabled = false
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {

            // Profile
            MenuButton(
                label = stringResource(R.string.main_menu_button_label_profile),
                onClick = {
                    mainViewModel.onNavigation()
                    toProfile()
                },
                enabled = !uiState.isNavigating,
                leadingIcon = UiModelMenuButtonIcon(
                    imageVector = GuidalIcons.Outlined.Profile,
                ),
                trailingIcon = UiModelMenuButtonIcon(
                    imageVector = GuidalIcons.Default.ChevronForward,
                    size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_12)
                )
            )

            // Settings
            MenuButton(
                label = stringResource(R.string.main_menu_button_label_settings),
                onClick = {
                    mainViewModel.onNavigation()
                    toSettings()
                },
                enabled = !uiState.isNavigating,
                leadingIcon = UiModelMenuButtonIcon(
                    imageVector = GuidalIcons.Outlined.Settings,
                ),
                trailingIcon = UiModelMenuButtonIcon(
                    imageVector = GuidalIcons.Default.ChevronForward,
                    size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_12)
                )
            )

            // Privacy
            MenuButton(
                label = stringResource(R.string.main_menu_button_label_privacy),
                onClick = {
                    mainViewModel.onNavigation()
                    toPrivacy()
                },
                enabled = !uiState.isNavigating,
                leadingIcon = UiModelMenuButtonIcon(
                    imageVector = GuidalIcons.Outlined.Privacy,
                ),
                trailingIcon = UiModelMenuButtonIcon(
                    imageVector = GuidalIcons.Default.ChevronForward,
                    size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_12)
                )
            )

            // About
            MenuButton(
                label = stringResource(R.string.main_menu_button_label_about),
                onClick = {
                    mainViewModel.onNavigation()
                    toAbout()
                },
                enabled = !uiState.isNavigating,
                leadingIcon = UiModelMenuButtonIcon(
                    imageVector = GuidalIcons.Outlined.About,
                ),
                trailingIcon = UiModelMenuButtonIcon(
                    imageVector = GuidalIcons.Default.ChevronForward,
                    size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_12)
                )
            )

            // Support
            MenuButton(
                label = stringResource(R.string.main_menu_button_label_support),
                onClick = {
                    mainViewModel.onNavigation()
                    toSupport()
                },
                enabled = !uiState.isNavigating,
                leadingIcon = UiModelMenuButtonIcon(
                    imageVector = GuidalIcons.Outlined.Support,
                ),
                trailingIcon = UiModelMenuButtonIcon(
                    imageVector = GuidalIcons.Default.ChevronForward,
                    size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_12)
                )
            )
        }
    }
}