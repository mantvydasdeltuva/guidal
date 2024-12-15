package com.guidal.app.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

// TODO KDoc
// TODO Android test
@Composable
internal fun RootNavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Graph.AUTHENTICATION
    ) {
        authNavigationGraph(navController)
        homeNavigationGraph(navController)
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
    const val DISCOVER = "discover"
    const val MENU = "menu"
    const val PROFILE = "menu/profile"
}