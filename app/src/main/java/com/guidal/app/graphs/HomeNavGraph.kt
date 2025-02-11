package com.guidal.app.graphs

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.guidal.feature.home.location.list.LocationListScreen
import com.guidal.feature.home.location.view.LocationViewScreen
import com.guidal.feature.home.main.MainScreen
import com.guidal.feature.home.post.main.PostScreen

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
            MainScreen(
                toWeather = {
                    // TODO Implement
//                    navController.navigate(Route.WEATHER)
                },
                toPost = { id ->
                    navController.navigate(
                        Route.POST.replace("{id}", id.toString())
                    )
                },
                toLocationList = { id ->
                    navController.navigate(
                        Route.LOCATIONS_LIST.replace("{id}", id.toString())
                    )
                },
            )
        }
        composable(
            route = Route.POST,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 250,
                        easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = LinearEasing
                    )
                )
            }
        ) { backStackEntry ->
            PostScreen(
                id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0,
                toBack = {
                    navController.popBackStack()
                },
            )
        }
        composable(
            route = Route.LOCATIONS_LIST,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 250,
                        easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = LinearEasing
                    )
                )
            }
        ) { backStackEntry ->
            LocationListScreen(
                id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0,
                toLocationView = { id ->
                    navController.navigate(
                        Route.LOCATION_VIEW.replace("{id}", id.toString())
                    )
                },
                toBack = {
                    navController.popBackStack()
                },
            )
        }
        composable(
            route = Route.LOCATION_VIEW,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 250,
                        easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = 200,
                        easing = LinearEasing
                    )
                )
            }
        ) { backStackEntry ->
            LocationViewScreen(
                id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0,
                toBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}
