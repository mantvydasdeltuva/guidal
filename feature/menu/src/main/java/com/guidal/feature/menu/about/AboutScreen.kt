package com.guidal.feature.menu.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import com.guidal.feature.menu.R

@Composable
fun AboutScreen(
    toBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val aboutViewModel: AboutViewModel = hiltViewModel()
    val uiState by aboutViewModel.uiState.collectAsState()

    // Component related
    val scrollState = rememberScrollState()

    // Urls
    // TODO Change string values to appropriate links
    val urlSocialMedia = stringResource(R.string.about_menu_button_url_social_media)
    val urlPrivacyPolicy = stringResource(R.string.about_menu_button_url_privacy_policy)
    val urlToS = stringResource(R.string.about_menu_button_url_tos)
    val urlLicenses = stringResource(R.string.about_menu_button_url_licenses)
    val urlRateApp = stringResource(R.string.about_menu_button_url_rate_app)

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(R.string.about_title),
                navigationIcon = UiModelTopAppBarIcon(
                    icon = GuidalIcons.Default.ArrowBack,
                    onClick = {
                        aboutViewModel.onNavigation()
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
            // Social Media
            MenuButton(
                label = stringResource(R.string.about_menu_button_label_social_media),
                onClick = {
                    aboutViewModel.toExternalLink(urlSocialMedia)
                },
                enabled = !uiState.isNavigating && uiState !is AboutUiState.Loading,
                trailingIcon = UiModelMenuButtonIcon(
                    imageVector = GuidalIcons.Outlined.Redirect,
                    size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_20)
                )
            )

            // Privacy Policy
            MenuButton(
                label = stringResource(R.string.about_menu_button_label_privacy_policy),
                onClick = {
                    aboutViewModel.toExternalLink(urlPrivacyPolicy)
                },
                enabled = !uiState.isNavigating && uiState !is AboutUiState.Loading,
                trailingIcon = UiModelMenuButtonIcon(
                    imageVector = GuidalIcons.Outlined.Redirect,
                    size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_20)
                )
            )

            // Terms of Service
            MenuButton(
                label = stringResource(R.string.about_menu_button_label_tos),
                onClick = {
                    aboutViewModel.toExternalLink(urlToS)
                },
                enabled = !uiState.isNavigating && uiState !is AboutUiState.Loading,
                trailingIcon = UiModelMenuButtonIcon(
                    imageVector = GuidalIcons.Outlined.Redirect,
                    size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_20)
                )
            )

            // Licenses
            MenuButton(
                label = stringResource(R.string.about_menu_button_label_licenses),
                onClick = {
                    aboutViewModel.toExternalLink(urlLicenses)
                },
                enabled = !uiState.isNavigating && uiState !is AboutUiState.Loading,
                trailingIcon = UiModelMenuButtonIcon(
                    imageVector = GuidalIcons.Outlined.Redirect,
                    size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_20)
                )
            )

            // Rate App
            MenuButton(
                label = stringResource(R.string.about_menu_button_label_rate_app),
                onClick = {
                    aboutViewModel.toExternalLink(urlRateApp)
                },
                enabled = !uiState.isNavigating && uiState !is AboutUiState.Loading,
                trailingIcon = UiModelMenuButtonIcon(
                    imageVector = GuidalIcons.Outlined.Redirect,
                    size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_20)
                )
            )

            // Guidal Version
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(horizontal = 28.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.about_menu_button_label_guidal_version),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(0.5f)
                )
                Text(
                    text = uiState.version,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .weight(0.5f)
                )
            }

            // Guidal Inline Logo
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = GuidalIcons.Default.GuidalInline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxSize(0.6f)
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewAboutScreen() {
    GuidalTheme {
        AboutScreen(
            toBack = {}
        )
    }
}