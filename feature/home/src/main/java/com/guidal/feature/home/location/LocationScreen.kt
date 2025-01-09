package com.guidal.feature.home.location

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.guidal.core.ui.R
import com.guidal.core.ui.components.HomeNavigationButton
import com.guidal.core.ui.components.LocationPreviewCard
import com.guidal.core.ui.components.Scaffold
import com.guidal.core.ui.components.TopAppBar
import com.guidal.core.ui.models.UiModelMenuButtonIcon
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
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            LocationPreviewCard(
                image = ImageBitmap.imageResource(id = R.drawable.transporation),
                title = "Holy Church of Saint Andrew",
                address = "Agiou Andreou 201, Patra 262 22",
                distance = "100 m",
                rating = 4.6f,
                isFavorite = true,
                onClick = { /* Handle card click */ },
                onFavoriteClick = { /* Handle favorite click */ }
            )

            LocationPreviewCard(
                image = ImageBitmap.imageResource(id = R.drawable.shops),
                title = "Holy Church of Saint Andrew",
                address = "Agiou Andreou 201, Patra 262 22",
                distance = "1000 km",
                rating = 0.5f,
                isFavorite = false,
                onClick = { /* Handle card click */ },
                onFavoriteClick = { /* Handle favorite click */ }
            )

            LocationPreviewCard(
                image = ImageBitmap.imageResource(id = R.drawable.trails),
                title = "Holy Church of Saint Andrew",
                address = "Agiou Andreou 201, Patra 262 22",
                distance = "0.6 km",
                rating = 3.0f,
                isFavorite = true,
                onClick = { /* Handle card click */ },
                onFavoriteClick = { /* Handle favorite click */ }
            )
        }
    }
}