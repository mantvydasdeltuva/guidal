package com.guidal.app.graphs

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.guidal.feature.discover.main.MainScreen

// TODO KDoc
// TODO Android test
internal fun NavGraphBuilder.discoverNavigationGraph(navController: NavController) {
    navigation(
        route = Graph.DISCOVER,
        startDestination = Route.DISCOVER
    ) {
        composable(
            route = Route.DISCOVER,
            enterTransition = {
                val initialRoute = this.initialState.destination.route
                when (initialRoute) {
                    Route.AUTHENTICATION, Route.LOGIN, Route.MENU -> fadeIn(
                        animationSpec = tween(
                            delayMillis = 100,
                            durationMillis = 300,
                            easing = LinearEasing
                        )
                    )
                    else -> fadeIn(
                        animationSpec = tween(
                            delayMillis = 100,
                            durationMillis = 100,
                            easing = LinearEasing
                        )
                    )
                }
            },
            popEnterTransition = {
                val initialRoute = this.initialState.destination.route
                when (initialRoute) {
                    Route.MENU -> fadeIn(
                        animationSpec = tween(
                            delayMillis = 100,
                            durationMillis = 300,
                            easing = LinearEasing
                        )
                    )
                    else -> fadeIn(
                        animationSpec = tween(
                            delayMillis = 100,
                            durationMillis = 100,
                            easing = LinearEasing
                        )
                    )
                }
            },
            exitTransition = {
                val targetRoute = this.targetState.destination.route
                when (targetRoute) {
                    Route.MENU -> fadeOut(
                        animationSpec = tween(
                            delayMillis = 100,
                            durationMillis = 300,
                            easing = LinearEasing
                        )
                    )
                    else -> fadeOut(
                        animationSpec = tween(
                            delayMillis = 100,
                            durationMillis = 100,
                            easing = LinearEasing
                        )
                    )
                }
            },
            popExitTransition = {
                val targetRoute = this.targetState.destination.route
                when (targetRoute) {
                    Route.MENU -> fadeOut(
                        animationSpec = tween(
                            delayMillis = 100,
                            durationMillis = 300,
                            easing = LinearEasing
                        )
                    )
                    else -> fadeOut(
                        animationSpec = tween(
                            delayMillis = 100,
                            durationMillis = 100,
                            easing = LinearEasing
                        )
                    )
                }
            },
        ) {
            MainScreen()
        }
    }
}
