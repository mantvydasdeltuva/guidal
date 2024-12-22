package com.guidal.authentication.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guidal.authentication.R
import com.guidal.core.utils.orDefault
import com.guidal.core.ui.components.Button
import com.guidal.core.ui.components.HorizontalDivider
import com.guidal.core.ui.components.OutlinedTextField
import com.guidal.core.ui.components.Scaffold
import com.guidal.core.ui.components.Snackbar
import com.guidal.core.ui.components.TopAppBar
import com.guidal.core.ui.models.UiModelTopAppBarIcon
import com.guidal.core.ui.theme.GuidalIcons
import com.guidal.core.ui.theme.GuidalTheme
import com.guidal.data.utils.saveUserToDataStore
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(
    toBack: () -> Unit,
    toHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val uiState by loginViewModel.uiState.collectAsState()

    // Related to component
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // Focus requesters
    val focusManager = LocalFocusManager.current
    val passwordFocusRequester = remember { FocusRequester() }
    val buttonBringIntoViewRequester = remember { BringIntoViewRequester() }

    val context = LocalContext.current

    // Checks
    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is LoginUiState.Error -> {
                snackbarHostState.showSnackbar(state.message, withDismissAction = true)
            }
            is LoginUiState.Success -> {
                saveUserToDataStore(context, state.user)
                toHome()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(R.string.login_title),
                navigationIcon = UiModelTopAppBarIcon(
                    icon = GuidalIcons.Default.ArrowBack,
                    onClick = { toBack() },
                    color = MaterialTheme.colorScheme.onSurface,
                    size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_16)
                )
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data -> Snackbar(data) }
            )
        },
    ) { innerPadding ->
        // Scrollable screen
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .padding(horizontal = 20.dp)
                .verticalScroll(scrollState)
                .clickable(interactionSource = null, indication = null) {
                    focusManager.clearFocus()
                }
        ) {
            // Email text field
            OutlinedTextField(
                label = stringResource(R.string.login_text_field_email_label),
                value = uiState.email,
                onValueChange = { loginViewModel.updateEmail(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        passwordFocusRequester.requestFocus()
                    }
                ),
                modifier = Modifier
                    .padding(top = 20.dp),
                isLoading = uiState is LoginUiState.Loading,
                isError = uiState.isEmailError,
                placeholder = stringResource(R.string.login_text_field_email_placeholder),
                supportingText = uiState.emailError.orDefault(stringResource(R.string.login_text_field_email_supporting_text))
            )

            // Password text field
            OutlinedTextField(
                label = stringResource(R.string.login_text_field_password_label),
                value = uiState.password,
                onValueChange = { loginViewModel.updatePassword(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        loginViewModel.login()
                    }
                ),
                modifier = Modifier
                    .focusRequester(passwordFocusRequester)
                    .onFocusChanged {
                        if (it.isFocused) {
                            coroutineScope.launch {
                                buttonBringIntoViewRequester.bringIntoView()
                            }
                        }
                    },
                isLoading = uiState is LoginUiState.Loading,
                isError = uiState.isPasswordError,
                placeholder = stringResource(R.string.login_text_field_password_placeholder),
                supportingText = uiState.passwordError.orDefault(stringResource(R.string.login_text_field_password_supporting_text)),
                visualTransformation = PasswordVisualTransformation()
            )

            // Visual divider between form and button
            HorizontalDivider(
                modifier = Modifier
                    .padding(top = 10.dp)
            )

            // Keeps button at the bottom
            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            // Log in button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .bringIntoViewRequester(buttonBringIntoViewRequester),
            ) {
                Button(
                    label = stringResource(R.string.login_button_label),
                    onClick = {
                        focusManager.clearFocus()
                        loginViewModel.login()
                    },
                    modifier = Modifier
                        .padding(vertical = 20.dp),
                    isLoading = uiState is LoginUiState.Loading
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    GuidalTheme {
        LoginScreen(
            toBack = {},
            toHome = {}
        )
    }
}