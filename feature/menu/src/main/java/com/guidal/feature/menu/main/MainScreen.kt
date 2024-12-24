package com.guidal.feature.menu.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.guidal.core.ui.R
import com.guidal.core.ui.components.MenuButton
import com.guidal.core.ui.components.Scaffold
import com.guidal.core.ui.components.TopAppBar
import com.guidal.core.ui.models.UiModelMenuButtonIcon
import com.guidal.core.ui.models.UiModelTopAppBarIcon
import com.guidal.core.ui.theme.GuidalIcons
import com.guidal.data.db.models.UserModel
import com.guidal.data.utils.getUserFromDataStore

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

    // User context
    val context = LocalContext.current
    val user: State<UserModel?> = getUserFromDataStore(context).collectAsState(null)

    // Component related
    val scrollState = rememberScrollState()

    // Ensures initial state when coming back with navigation stack
    LaunchedEffect(Unit) {
        mainViewModel.resetState()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Welcome, ${user.value?.name}${user.value?.surname?.takeIf { it.isNotEmpty() }?.let { " $it" } ?: ""}!",
                navigationIcon = UiModelTopAppBarIcon(
                    icon = GuidalIcons.Default.Guidal,
                    onClick = { },
                    color = MaterialTheme.colorScheme.primary,
                    isEnabled = false
                ),
                modifier = Modifier
                    .padding(horizontal = 4.dp)
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
                label = "Profile",
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
                    size = dimensionResource(R.dimen.icon_size_12)
                )
            )

            // Settings
            MenuButton(
                label = "Settings",
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
                    size = dimensionResource(R.dimen.icon_size_12)
                )
            )

            // Privacy
            MenuButton(
                label = "Privacy",
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
                    size = dimensionResource(R.dimen.icon_size_12)
                )
            )

            // About
            MenuButton(
                label = "About",
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
                    size = dimensionResource(R.dimen.icon_size_12)
                )
            )

            // Support
            MenuButton(
                label = "Support",
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
                    size = dimensionResource(R.dimen.icon_size_12)
                )
            )
        }
    }
}