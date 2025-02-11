package com.guidal.app.graphs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.guidal.app.R
import com.guidal.core.ui.components.BottomAppBar
import com.guidal.core.ui.components.Scaffold
import com.guidal.core.ui.models.UiModelBottomAppBarItem
import com.guidal.core.ui.theme.GuidalIcons

// TODO KDoc
// TODO Android test
@Composable
internal fun RootNavigationGraph(navController: NavHostController) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val bottomAppBarRoutes = setOf(Route.HOME, Route.DISCOVER, Route.MENU)

    var selectedItemIndex = remember { mutableIntStateOf(0) }

    selectedItemIndex.intValue = when (currentDestination?.route) {
        Route.HOME -> 0
        Route.DISCOVER -> 1
        Route.MENU -> 2
        else -> selectedItemIndex.intValue
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = currentDestination?.route in bottomAppBarRoutes,
                enter = (
                    fadeIn(
                        animationSpec = tween(
                            delayMillis = 400,
                            durationMillis = 100,
                            easing = LinearEasing
                        )
                    )
                    + slideInVertically(
                        animationSpec = tween(
                            delayMillis = 400,
                            durationMillis = 100,
                            easing = EaseInOut
                        ),
                        initialOffsetY = { it }
                    )
                    + expandVertically(
                        animationSpec = tween(
                            delayMillis = 400,
                            durationMillis = 100,
                            easing = EaseInOut
                        )
                    )
                ),
                exit = (
                    fadeOut(
                        animationSpec = tween(
                            durationMillis = 100,
                            easing = LinearEasing
                        )
                    )
                    + slideOutVertically(
                        animationSpec = tween(
                            durationMillis = 100,
                            easing = EaseInOut
                        ),
                        targetOffsetY = { it }
                    )
                    + shrinkVertically(
                        animationSpec = tween(
                            durationMillis = 100,
                            easing = EaseInOut
                        )
                    )
                )
            ) {
                BottomAppBar(
                    items = listOf(
                        UiModelBottomAppBarItem(
                            label = stringResource(R.string.home_bottom_bar_label),
                            selectedIcon = GuidalIcons.Default.Home,
                            unselectedIcon = GuidalIcons.Outlined.Home,
                            enabled = currentDestination?.route != Route.HOME
                        ),
                        UiModelBottomAppBarItem(
                            label = stringResource(R.string.discover_bottom_bar_label),
                            selectedIcon = GuidalIcons.Default.Discover,
                            unselectedIcon = GuidalIcons.Outlined.Discover,
                            enabled = currentDestination?.route != Route.DISCOVER
                        ),
                        UiModelBottomAppBarItem(
                            label = stringResource(R.string.menu_bottom_bar_label),
                            selectedIcon = GuidalIcons.Default.Menu,
                            unselectedIcon = GuidalIcons.Outlined.Menu,
                            enabled = currentDestination?.route != Route.MENU
                        )
                    ),
                    selectedItemIndex = selectedItemIndex.intValue,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navController.navigate(Graph.HOME)
                            1 -> navController.navigate(Graph.DISCOVER)
                            2 -> navController.navigate(Graph.MENU)
                        }
                    }
                )
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier
            .systemBarsPadding()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Graph.AUTHENTICATION,
            modifier = Modifier
                .padding(innerPadding)
        ) {
            authNavigationGraph(navController)
            homeNavigationGraph(navController)
            discoverNavigationGraph(navController)
            menuNavigationGraph(navController)
        }
    }
}

internal object Graph {
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DISCOVER = "discover_graph"
    const val MENU = "menu_graph"
}

internal object Route {
    const val AUTHENTICATION = "auth"
    const val LOGIN = "auth/login"
    const val SIGNUP = "auth/signup"
    const val HOME = "home"
    const val WEATHER = "home/weather"
    const val POST = "home/post/{id}"
    const val LOCATIONS_LIST = "home/locations_list/{id}"
    const val LOCATION_VIEW = "home/location_view/{id}"
    const val DISCOVER = "discover"
    const val MENU = "menu"
    const val PROFILE = "menu/profile"
    const val SETTINGS = "menu/settings"
    const val PRIVACY = "menu/privacy"
    const val ABOUT = "menu/about"
    const val SUPPORT = "menu/support"
}

internal object GraphRoutes {
    val AUTHENTICATION = listOf(Route.AUTHENTICATION, Route.LOGIN, Route.SIGNUP)
    val HOME = listOf(Route.HOME, Route.WEATHER, Route.POST, Route.LOCATIONS_LIST)
    val DISCOVER = listOf(Route.DISCOVER)
    val MENU = listOf(Route.MENU, Route.PROFILE, Route.SETTINGS, Route.PRIVACY, Route.ABOUT, Route.SUPPORT)
}