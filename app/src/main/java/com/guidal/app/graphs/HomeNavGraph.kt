package com.guidal.app.graphs

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.guidal.home.HomeScreen

// TODO KDoc
// TODO Android test
internal fun NavGraphBuilder.homeNavigationGraph(navController: NavController) {
    navigation(
        route = Graph.HOME,
        startDestination = Route.HOME
    ) {
        composable(
            route = Route.HOME,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(
                        delayMillis = 100,
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            }
        ) {
            HomeScreen()
        }
    }
}
