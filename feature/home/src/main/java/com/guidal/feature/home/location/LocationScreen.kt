package com.guidal.feature.home.location

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.guidal.core.ui.components.Scaffold
import com.guidal.core.ui.components.TopAppBar
import com.guidal.core.ui.models.UiModelTopAppBarIcon
import com.guidal.core.ui.theme.GuidalIcons

@Composable
fun LocationScreen(
    id: Int,
    toBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val locationViewModel: LocationViewModel = viewModel()
    val uiState by locationViewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()

    // TODO: SWITCH TO FETCHING DATA FROM THE DATABASE
    // temporary
    val locationTitle = when (id) {
        4 -> "Must Visit"
        5 -> "Sightseeing"
        6 -> "Restaurants"
        7 -> "Beaches"
        8 -> "Night Life"
        9 -> "Favorites"
        else -> "Location"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = locationTitle,
                navigationIcon = UiModelTopAppBarIcon(
                    icon = GuidalIcons.Default.ArrowBack,
                    onClick = {
                        toBack()
                    },
                    color = MaterialTheme.colorScheme.onSurface,
                    size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_16)
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
        }
    }
}