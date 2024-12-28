package com.guidal.feature.home.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.guidal.core.ui.components.HomeNavigationButton
import com.guidal.core.ui.components.Scaffold
import com.guidal.core.ui.components.TopAppBar
import com.guidal.core.ui.models.UiModelMenuButtonIcon
import com.guidal.core.ui.models.UiModelTopAppBarIcon
import com.guidal.core.ui.theme.GuidalIcons
import com.guidal.data.db.models.UserModel
import com.guidal.data.utils.getUserFromDataStore
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val user: State<UserModel?> = getUserFromDataStore(context).collectAsState(null)

    // TODO: Move to UI state and view model
    val buttons = listOf(
        HomeNavigationButtonModel("Transportation", "Post", onClick = {}, sectionIcon = UiModelMenuButtonIcon(imageVector = GuidalIcons.Default.Commute)),
        HomeNavigationButtonModel("Shops", "Post", onClick = {}, sectionIcon = UiModelMenuButtonIcon(imageVector = GuidalIcons.Default.Shop)),
        HomeNavigationButtonModel("Trails", "Post", onClick = {}, sectionIcon = UiModelMenuButtonIcon(imageVector = GuidalIcons.Default.Hiking)),
        HomeNavigationButtonModel("Must Visit", "Category", onClick = {}, sectionIcon = UiModelMenuButtonIcon(imageVector = GuidalIcons.Default.CircledStar)),
        HomeNavigationButtonModel("Sightseeing", "Category", onClick = {}, sectionIcon = UiModelMenuButtonIcon(imageVector = GuidalIcons.Default.Museum)),
        HomeNavigationButtonModel("Restaurants", "Category", onClick = {}, sectionIcon = UiModelMenuButtonIcon(imageVector = GuidalIcons.Default.Restaurant)),
        HomeNavigationButtonModel("Beaches", "Category", onClick = {}, sectionIcon = UiModelMenuButtonIcon(imageVector = GuidalIcons.Default.Beach)),
        HomeNavigationButtonModel("Night Life", "Category", onClick = {}, sectionIcon = UiModelMenuButtonIcon(imageVector = GuidalIcons.Default.NightLife)),
        HomeNavigationButtonModel("Favorites", "Category", onClick = {}, sectionIcon = UiModelMenuButtonIcon(imageVector = GuidalIcons.Outlined.Favorite))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Patras",
                navigationIcon = UiModelTopAppBarIcon(
                    icon = GuidalIcons.Default.Guidal,
                    onClick = { },
                    color = MaterialTheme.colorScheme.primary,
                    isEnabled = false
                ),
                modifier = Modifier
                    .padding(horizontal = 4.dp)
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
                    style = MaterialTheme.typography.titleMedium,
                    text = "$dayOfWeek, $formattedDate" // Example: "Monday, 2024-12-29"
                )
            }

            // Buttons Grid Section
            LazyVerticalGrid(
                columns = GridCells.Fixed(3), // 3 buttons per row
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp),
            ) {
                items(buttons.size) { index ->
                    val button = buttons[index]
                    HomeNavigationButton(
                        label = button.label,
                        type = button.type,
                        onClick = button.onClick,
                        sectionIcon = button.sectionIcon,
                        enabled = button.enabled,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}