package com.guidal.authentication.authentication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guidal.core.ui.R
import com.guidal.core.ui.components.Button
import com.guidal.core.ui.components.HorizontalDivider
import com.guidal.core.ui.components.OutlinedButton
import com.guidal.core.ui.components.Scaffold
import com.guidal.core.ui.components.Snackbar
import com.guidal.core.ui.theme.GuidalIcons
import com.guidal.core.ui.theme.GuidalTheme
import com.guidal.data.utils.saveUserToDataStore

// TODO Extract string resources
@Composable
fun AuthenticationScreen(
    toLogin: () -> Unit,
    toSignup: () -> Unit,
    toHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val authenticationViewModel: AuthenticationViewModel = hiltViewModel()
    val uiState by authenticationViewModel.uiState.collectAsState()

    // Related to component
    val snackbarHostState = remember { SnackbarHostState() }

    // Ensures initial state when coming back with navigation stack
    LaunchedEffect(Unit) {
        authenticationViewModel.resetState()
    }

    val context = LocalContext.current

    // Checks
    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is AuthenticationUiState.Error -> {
                snackbarHostState.showSnackbar(state.message, withDismissAction = true)
            }
            is AuthenticationUiState.Success -> {
                saveUserToDataStore(context, state.user)
                toHome()
            }
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data -> Snackbar(data) }
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = modifier
            .systemBarsPadding()
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .fillMaxHeight(0.8f)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Top
            ) {
                // Guidal logo and location field
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Bottom)
                ) {
                    Icon(
                        imageVector = GuidalIcons.Default.GuidalLogo,
                        contentDescription = "Guidal",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(120.dp)
                    )
                    Text(
                        text = "Patras, Greece",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }

                // Buttons field
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
                ) {
                    // Google button
                    androidx.compose.material3.OutlinedButton(
                        onClick = { authenticationViewModel.google() },
                        enabled = uiState !is AuthenticationUiState.Loading && !uiState.isNavigating,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceBright,
                            contentColor = Color.Unspecified
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                    ) {
                        Icon(
                            imageVector = GuidalIcons.Default.Google,
                            contentDescription = "Sign in with Google",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(dimensionResource(R.dimen.icon_size_24))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Sign in with Google",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    HorizontalDivider()

                    // Log in button
                    Button(
                        label = "Log in",
                        onClick = {
                            authenticationViewModel.onNavigation()
                            toLogin()
                        },
                        isEnabled = uiState !is AuthenticationUiState.Loading && !uiState.isNavigating
                    )

                    // Sign up button
                    OutlinedButton(
                        label = "Sign up",
                        onClick = {
                            authenticationViewModel.onNavigation()
                            toSignup()
                        },
                        isEnabled = uiState !is AuthenticationUiState.Loading && !uiState.isNavigating
                    )
                }

                // Loading indicator
                if (uiState is AuthenticationUiState.Loading) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(dimensionResource(R.dimen.icon_size_24)),
                                color = MaterialTheme.colorScheme.primary
                            )

                    }
                }

                // Text button field
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    TextButton(
                        onClick = { authenticationViewModel.guest() },
                        enabled = uiState !is AuthenticationUiState.Loading && !uiState.isNavigating,
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = "Continue as Guest",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = GuidalIcons.Default.ArrowForward,
                            contentDescription = "Continue as guest",
                            modifier = Modifier.size(10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewAuthenticationScreen() {
    GuidalTheme {
        AuthenticationScreen(
            toLogin = {},
            toSignup = {},
            toHome = {}
        )
    }
}