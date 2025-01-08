package com.guidal.feature.menu.privacy

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guidal.core.ui.components.HorizontalDivider
import com.guidal.core.ui.components.Scaffold
import com.guidal.core.ui.components.Switch
import com.guidal.core.ui.components.TopAppBar
import com.guidal.core.ui.models.UiModelTopAppBarIcon
import com.guidal.core.ui.theme.GuidalIcons
import com.guidal.core.ui.theme.GuidalTheme
import com.guidal.feature.menu.R

@Composable
fun PrivacyScreen(
    toBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val privacyViewModel: PrivacyViewModel = hiltViewModel()
    val uiState by privacyViewModel.uiState.collectAsState()

    // Permission request launcher
    val requestLocationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isLocationEnabled =
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        privacyViewModel.updateLocationState(isLocationEnabled)
    }

    // Component related
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(R.string.privacy_title),
                navigationIcon = UiModelTopAppBarIcon(
                    icon = GuidalIcons.Default.ArrowBack,
                    onClick = {
                        privacyViewModel.onNavigation()
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
            // Location Permission
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.privacy_location_permission_label),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Switch(
                        checked = uiState.isLocationEnabled,
                        modifier = Modifier.graphicsLayer(scaleX = 0.8f, scaleY = 0.8f),
                        onCheckedChange = { checked ->
                            if (checked) {
                                requestLocationPermissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    )
                                )
                            } else {
                                privacyViewModel.openAppSettings()
                            }
                        }
                    )
                }

                Text(
                    text = stringResource(R.string.privacy_location_permission_description),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                HorizontalDivider(
                    modifier = Modifier.padding(top = 18.dp, bottom = 2.dp)
                )
            }

            // Collection Permission
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.privacy_collection_permission_label),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Switch(
                        checked = uiState.isCollectionEnabled,
                        modifier = Modifier.graphicsLayer(scaleX = 0.8f, scaleY = 0.8f),
                        // TODO Implement collection
                        onCheckedChange = { checked ->
                            privacyViewModel.updateCollectionState(
                                checked
                            )
                      },
                    )
                }

                Text(
                    text = stringResource(R.string.privacy_collection_permission_description),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                HorizontalDivider(
                    modifier = Modifier.padding(top = 18.dp, bottom = 2.dp)
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewPrivacyScreen() {
    GuidalTheme {
        PrivacyScreen(
            toBack = {}
        )
    }
}