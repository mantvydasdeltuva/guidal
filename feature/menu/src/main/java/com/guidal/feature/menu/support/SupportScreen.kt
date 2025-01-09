package com.guidal.feature.menu.support

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
fun SupportScreen(
    toFAQ: () -> Unit,
    toReport: () -> Unit,
    toBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val supportViewModel: SupportViewModel = hiltViewModel()
    val uiState by supportViewModel.uiState.collectAsState()

    // Component related
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(R.string.support_title),
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
            // FAQ
            MenuButton(
                label = stringResource(R.string.support_menu_button_label_faq),
                onClick = {
                    // TODO: Uncomment once navigation implemented
                    //supportViewModel.onNavigation()
                    toFAQ()
                },
                enabled = !uiState.isNavigating && uiState !is SupportUiState.Loading,
                trailingIcon = UiModelMenuButtonIcon(
                    imageVector = GuidalIcons.Default.ChevronForward,
                    size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_12)
                )
            )

            // Report
            MenuButton(
                label = stringResource(R.string.support_menu_button_label_report),
                onClick = {
                    // TODO: Uncomment once navigation implemented
                    //supportViewModel.onNavigation()
                    toReport()
                },
                enabled = !uiState.isNavigating && uiState !is SupportUiState.Loading,
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
private fun PreviewSupportScreen() {
    GuidalTheme {
        SupportScreen(
            toBack = {},
            toFAQ = {},
            toReport = {}
        )
    }
}