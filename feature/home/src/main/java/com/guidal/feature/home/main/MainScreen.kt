package com.guidal.feature.home.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guidal.core.ui.components.HomeNavigationButton
import com.guidal.core.ui.components.Scaffold
import com.guidal.core.ui.components.TopAppBar
import com.guidal.core.ui.models.UiModelMenuButtonIcon
import com.guidal.core.ui.models.UiModelTopAppBarIcon
import com.guidal.core.ui.theme.GuidalIcons
import com.guidal.feature.home.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val uiState by mainViewModel.uiState.collectAsState()

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
                .padding(horizontal = 10.dp)
        ) {
            // TODO: REMOVE AFTER WEATHER IMPLEMENTATION
            // Date section
            val currentDate = LocalDate.now()
            val dayOfWeek = currentDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
            val formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelLarge,
                    text = "$dayOfWeek, $formattedDate" // Example: "Monday, 2024-12-29"
                )
            }

            if (uiState.isSearchEnabled) {
                // TODO Search category suggestions
            } else {
                // Buttons Grid Section
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3), // 3 buttons per row
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp),
                ) {
                    items(uiState.categories) { category ->
                        HomeNavigationButton(
                            label = category.name,
                            type = category.type,
                            onClick = {}, // TODO Implement onclick mapping, similar to icon mapping
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