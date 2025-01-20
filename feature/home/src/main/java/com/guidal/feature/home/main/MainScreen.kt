package com.guidal.feature.home.main

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guidal.core.ui.components.HomeNavigationButton
import com.guidal.core.ui.components.Scaffold
import com.guidal.core.ui.components.TopAppBar
import com.guidal.core.ui.components.WeatherWidget
import com.guidal.core.ui.models.UiModelMenuButtonIcon
import com.guidal.core.ui.models.UiModelTopAppBarIcon
import com.guidal.core.ui.skeletons.HomeNavigationButtonSkeleton
import com.guidal.core.ui.skeletons.WeatherWidgetSkeleton
import com.guidal.core.ui.theme.GuidalIcons
import com.guidal.feature.home.R

@Composable
fun MainScreen(
    toWeather: () -> Unit,
    toPost: (id: Int) -> Unit,
    toLocation: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val uiState by mainViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        mainViewModel.resetState()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(R.string.home_city_name),
                navigationIcon = UiModelTopAppBarIcon(
                    icon = GuidalIcons.Default.Guidal,
                    onClick = { },
                    color = MaterialTheme.colorScheme.primary,
                    isEnabled = false
                ),
                actions = listOf(
                    UiModelTopAppBarIcon(
                        icon = GuidalIcons.Default.Search,
                        onClick = { mainViewModel.updateSearchState() },
                        color = MaterialTheme.colorScheme.onSurface,
                        isEnabled = !uiState.isNavigating,
                        size = 20.dp
                    )
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val state = uiState) {
                is MainUiState.Loading -> {
                    WeatherWidgetSkeleton()

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 20.dp),
                    ) {
                        items(9) {
                            HomeNavigationButtonSkeleton()
                        }
                    }
                }
                else -> {
                    if (state.isSearchEnabled) {
                        // TODO Search category suggestions
                    } else {
                        if (state.forecast.isNotEmpty()) {
                            WeatherWidget(
                                items = state.forecast,
                                onClick = {
                                    // TODO: Uncomment once Weather screen is implemented
                                    //mainViewModel.onNavigation()
                                    toWeather()
                                },
                                enabled = !uiState.isNavigating
                            )
                        }

                        // Buttons Grid Section
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 20.dp),
                        ) {
                            items(state.categories) { category ->
                                HomeNavigationButton(
                                    label = getCategoryName(
                                        LocalContext.current, category.name
                                    ),
                                    type = category.type,
                                    onClick = {
                                        mainViewModel.onNavigation()
                                        when (category.type) {
                                            "Post" -> {
                                                toPost(category.id)
                                            }
                                            "Location" -> {
                                                toLocation(category.id)
                                            }
                                            else -> {}
                                        }
                                    },
                                    enabled = !uiState.isNavigating,
                                    sectionIcon = UiModelMenuButtonIcon(
                                        imageVector = getCategoryIcon(category.name)
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun getCategoryIcon(name: String): ImageVector {
    val categoryIconMap = mapOf(
        "Transportation" to GuidalIcons.Category.Commute,
        "Shops" to GuidalIcons.Category.Shop,
        "Trails" to GuidalIcons.Category.Hiking,
        "Must Visit" to GuidalIcons.Category.CircledStar,
        "Sightseeing" to GuidalIcons.Category.Museum,
        "Restaurants" to GuidalIcons.Category.Restaurant,
        "Beaches" to GuidalIcons.Category.Beach,
        "Night Life" to GuidalIcons.Category.NightLife,
        "Favorites" to GuidalIcons.Category.Favorite
    )
    return categoryIconMap[name] ?: GuidalIcons.Default.Guidal
}

private fun getCategoryName(context: Context, name: String): String {
    val categoryNameMap = mapOf(
        "Transportation" to R.string.transportation_title,
        "Shops" to R.string.shops_title,
        "Trails" to R.string.trails_title,
        "Must Visit" to R.string.must_visit_title,
        "Sightseeing" to R.string.sightseeing_title,
        "Restaurants" to R.string.restaurants_title,
        "Beaches" to R.string.beaches_title,
        "Night Life" to R.string.night_life_title,
        "Favorites" to R.string.favorites_title
    )
    val stringResId = categoryNameMap[name]
    return if (stringResId != null) context.getString(stringResId) else "Category"
}