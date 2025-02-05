package com.guidal.feature.home.location.list

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.guidal.feature.home.R
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
    val locationListViewModel: LocationListViewModel = hiltViewModel()
    val uiState by locationListViewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // TODO: SWITCH TO FETCHING DATA FROM THE DATABASE
    // TODO: ADD TRANSLATIONS AFTER DATABASE FETCHING
    // temporary
    val locationTitle = when (id) {
        4 -> R.string.must_visit_title
        5 -> R.string.sightseeing_title
        6 -> R.string.restaurants_title
        7 -> R.string.beaches_title
        8 -> R.string.night_life_title
        9 -> R.string.favorites_title
        else -> R.string.location_title
    }

    LaunchedEffect(Unit) {
        locationListViewModel.fetch(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(locationTitle),
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
                    image = ImageBitmap.imageResource(
                        id = getLocationImageResId(
                            LocalContext.current,
                            it.id
                        )
                    ),
                    title = it.title,
                    address = it.address,
                    distance = "100 m",
                    rating = it.rating,
                    isFavorite = false,
                    onClick = { toLocationView(it.id) },
                    onFavoriteClick = {}
                )
            }
        }
    }
}

private fun getLocationImageResId(context: Context, locationId: Int?): Int {
    // Dynamically set images to locations
    return locationId?.let { id ->
        context.resources.getIdentifier(
            "image_location_$id", // Example: image_location_5.jpg
            "drawable",
            context.packageName
        )
    }.takeIf { it != 0 } ?: R.drawable.sample_image
}