package com.guidal.feature.home.location.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.guidal.core.ui.R
import com.guidal.core.ui.components.LocationPreviewCard
import com.guidal.core.ui.components.Scaffold
import com.guidal.core.ui.components.TopAppBar
import com.guidal.core.ui.models.UiModelTopAppBarIcon
import com.guidal.core.ui.theme.GuidalIcons
import kotlinx.coroutines.launch

@Composable
fun LocationListScreen(
    id: Int,
    toBack: () -> Unit,
    toLocationView: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val locationViewModel: LocationListViewModel = viewModel()
    val uiState by locationViewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // TODO: SWITCH TO FETCHING DATA FROM THE DATABASE
    // TODO: ADD TRANSLATIONS AFTER DATABASE FETCHING
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
                    size = dimensionResource(R.dimen.icon_size_16)
                )
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = scrollState.value.toFloat() >= 550f,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            scrollState.animateScrollTo(0)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                ) {
                    Icon(
                        imageVector = GuidalIcons.Default.ChevronForward,
                        contentDescription = "Scroll to Top",
                        modifier = Modifier.rotate(-90f)
                    )
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            // TODO: TEMPORARY DATA, REMOVE AFTER IMPLEMENTATION
            LocationPreviewCard(
                image = ImageBitmap.imageResource(id = R.drawable.transporation),
                title = "Bus Station",
                address = "Agiou Andreou 201, Patras 262 22",
                distance = "100 m",
                rating = 4.6f,
                isFavorite = true,
                onClick = { toLocationView(0) },
                onFavoriteClick = { /* Handle favorite click */ }
            )

            LocationPreviewCard(
                image = ImageBitmap.imageResource(id = R.drawable.shops),
                title = "Sklavenitis",
                address = "Gerasimou Sklavou 7, Patras 242 21",
                distance = "1000 km",
                rating = 0.5f,
                isFavorite = false,
                onClick = { /* Handle card click */ },
                onFavoriteClick = { /* Handle favorite click */ }
            )

            LocationPreviewCard(
                image = ImageBitmap.imageResource(id = R.drawable.trails),
                title = "Ultimate Trail",
                address = "Agiou 123, Patras 213 42",
                distance = "0.6 km",
                rating = 3.0f,
                isFavorite = true,
                onClick = { /* Handle card click */ },
                onFavoriteClick = { /* Handle favorite click */ }
            )
        }
    }
}