package com.guidal.feature.menu.settings

import android.content.Intent
import android.provider.Settings
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.guidal.core.ui.components.MenuButton
import com.guidal.core.ui.components.Scaffold
import com.guidal.core.ui.components.TopAppBar
import com.guidal.core.ui.models.UiModelMenuButtonIcon
import com.guidal.core.ui.models.UiModelTopAppBarIcon
import com.guidal.core.ui.theme.GuidalIcons
import com.guidal.core.ui.theme.GuidalTheme
import com.guidal.feature.menu.R

@Composable
fun SettingsScreen(
    toLanguage: () -> Unit,
    toAppearance: () -> Unit,
    toBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val supportViewModel: SettingsViewModel = hiltViewModel()
    val uiState by supportViewModel.uiState.collectAsState()

    val context = LocalContext.current

    // Component related
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(R.string.settings_title),
                navigationIcon = UiModelTopAppBarIcon(
                    icon = GuidalIcons.Default.ArrowBack,
                    onClick = {
                        supportViewModel.onNavigation()
                        toBack()
                    },
                    color = MaterialTheme.colorScheme.onSurface,
                    isEnabled = !uiState.isNavigating,
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
            // Language
            MenuButton(
                label = stringResource(R.string.settings_menu_button_label_language),
                onClick = {
                    val intent = Intent(Settings.ACTION_LOCALE_SETTINGS) // Opens Display settings
                    context.startActivity(intent)

                    // TODO: Uncomment once better navigation implemented
                    //supportViewModel.onNavigation()
                    //toLanguage()
                },
                enabled = !uiState.isNavigating && uiState !is SettingsUiState.Loading,
                trailingIcon = UiModelMenuButtonIcon(
                    imageVector = GuidalIcons.Default.ChevronForward,
                    size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_12)
                )
            )

            // Appearance
            MenuButton(
                label = stringResource(R.string.settings_menu_button_label_appearance),
                onClick = {
                    val intent = Intent(Settings.ACTION_DISPLAY_SETTINGS) // Opens Display settings
                    context.startActivity(intent)

                    // TODO: Uncomment once better navigation implemented
                    //supportViewModel.onNavigation()
                    //toAppearance()
                },
                enabled = !uiState.isNavigating && uiState !is SettingsUiState.Loading,
                trailingIcon = UiModelMenuButtonIcon(
                    imageVector = GuidalIcons.Default.ChevronForward,
                    size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_12)
                )
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewSettingsScreen() {
    GuidalTheme {
        SettingsScreen(
            toBack = {},
            toLanguage = {},
            toAppearance = {}
        )
    }
}