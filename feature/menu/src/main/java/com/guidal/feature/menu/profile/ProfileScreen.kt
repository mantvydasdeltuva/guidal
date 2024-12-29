package com.guidal.feature.menu.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guidal.core.ui.components.MenuButton
import com.guidal.core.ui.components.Scaffold
import com.guidal.core.ui.components.TopAppBar
import com.guidal.core.ui.models.UiModelMenuButtonIcon
import com.guidal.core.ui.models.UiModelTopAppBarIcon
import com.guidal.core.ui.theme.GuidalIcons
import com.guidal.core.ui.theme.GuidalTheme
import com.guidal.core.utils.orDefault

@Composable
fun ProfileScreen(
    toBack: () -> Unit,
    toNameSurname: () -> Unit,
    toEmail: () -> Unit,
    toPassword: () -> Unit,
    toGender: () -> Unit,
    toCountry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val uiState by profileViewModel.uiState.collectAsState()

    // Component related
    val scrollState = rememberScrollState()

    // TODO: Extract string resource
    Scaffold(
        topBar = {
            TopAppBar(
                title = "Profile",
                navigationIcon = UiModelTopAppBarIcon(
                    icon = GuidalIcons.Default.ArrowBack,
                    onClick = { toBack() },
                    color = MaterialTheme.colorScheme.onSurface,
                    size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_16)
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
            when (val state = uiState) {
                is ProfileUiState.Loading -> {
                    // TODO: Skeleton loading indication
                }
                is ProfileUiState.Error -> {
                    // TODO: Error state
                }
                is ProfileUiState.Idle -> {
                    // Name&Surname
                    MenuButton(
                        label = "${state.user.name} ${state.user.surname}",
                        onClick = {
                            profileViewModel.onNavigation()
                            toNameSurname()
                        },
                        enabled = !uiState.isNavigating,
                        leadingIcon = UiModelMenuButtonIcon(
                            imageVector = GuidalIcons.Outlined.Id,
                        ),
                        trailingIcon = UiModelMenuButtonIcon(
                            imageVector = GuidalIcons.Outlined.Edit,
                            size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_20)
                        )
                    )

                    // Email
                    MenuButton(
                        label = state.user.email,
                        onClick = {
                            profileViewModel.onNavigation()
                            toEmail()
                        },
                        enabled = !uiState.isNavigating,
                        leadingIcon = UiModelMenuButtonIcon(
                            imageVector = GuidalIcons.Outlined.Email,
                        ),
                        trailingIcon = UiModelMenuButtonIcon(
                            imageVector = GuidalIcons.Outlined.Edit,
                            size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_20)
                        )
                    )

                    // Password
                    MenuButton(
                        label = state.user.password.replace(Regex("."), "*"),
                        onClick = {
                            profileViewModel.onNavigation()
                            toPassword()
                        },
                        enabled = !uiState.isNavigating,
                        leadingIcon = UiModelMenuButtonIcon(
                            imageVector = GuidalIcons.Outlined.Password,
                        ),
                        trailingIcon = UiModelMenuButtonIcon(
                            imageVector = GuidalIcons.Outlined.Edit,
                            size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_20)
                        )
                    )

                    // Gender
                    MenuButton(
                        label = state.user.gender.orDefault("Other"),
                        onClick = {
                            profileViewModel.onNavigation()
                            toGender()
                        },
                        enabled = !uiState.isNavigating,
                        leadingIcon = UiModelMenuButtonIcon(
                            imageVector = when (state.user.gender.orDefault("Other")) {
                                "Male" -> GuidalIcons.Gender.Man
                                "Woman" -> GuidalIcons.Gender.Woman
                                else -> GuidalIcons.Gender.Other
                            }
                        ),
                        trailingIcon = UiModelMenuButtonIcon(
                            imageVector = GuidalIcons.Default.ChevronForward,
                            size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_12)
                        )
                    )

                    // Country
                    MenuButton(
                        label = state.user.country.orDefault("Other"),
                        onClick = {
                            profileViewModel.onNavigation()
                            toCountry()
                        },
                        enabled = !uiState.isNavigating,
                        leadingIcon = UiModelMenuButtonIcon(
                            imageVector = when (state.user.country.orDefault("Other")) {
                                "Greece" -> GuidalIcons.Country.Greece
                                "Lithuania" -> GuidalIcons.Country.Lithuania
                                else -> GuidalIcons.Country.Other
                            },
                            tint = Color.Unspecified
                        ),
                        trailingIcon = UiModelMenuButtonIcon(
                            imageVector = GuidalIcons.Default.ChevronForward,
                            size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_12)
                        )
                    )

                    // Delete Account
                    MenuButton(
                        label = "Delete Account",
                        onClick = {
                            // TODO: Implement prompt for account deletion
                        },
                        enabled = !uiState.isNavigating,
                        leadingIcon = UiModelMenuButtonIcon(
                            imageVector = GuidalIcons.Outlined.Delete,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                else -> {}
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewProfileScreen() {
    GuidalTheme {
        ProfileScreen(
            toBack = {},
            toNameSurname = {},
            toEmail = {},
            toPassword = {},
            toGender = {},
            toCountry = {}
        )
    }
}