package com.guidal.feature.home.location

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.guidal.core.ui.R
import com.guidal.core.ui.components.LocationPreviewCard
import com.guidal.core.ui.components.Scaffold
import com.guidal.core.ui.components.TopAppBar
import com.guidal.core.ui.models.UiModelTopAppBarIcon
import com.guidal.core.ui.theme.GuidalIcons
import kotlinx.coroutines.launch

@Composable
fun LocationScreen(
    id: Int,
    toBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val locationViewModel: LocationViewModel = hiltViewModel()
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

    LaunchedEffect(Unit) {
        locationViewModel.fetch(id)
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
            uiState.locations.forEach {
                LocationPreviewCard(
                    image = ImageBitmap.imageResource(id = R.drawable.transporation),
                    title = it.title,
                    address = it.address,
                    distance = "100 m",
                    rating = it.rating,
                    isFavorite = false,
                    onClick = {},
                    onFavoriteClick = {}
                )
            }
        }
    }
}