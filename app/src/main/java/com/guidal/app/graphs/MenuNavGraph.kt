package com.guidal.app.graphs

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.guidal.feature.menu.main.MainScreen
import com.guidal.feature.menu.privacy.PrivacyScreen
import com.guidal.feature.menu.profile.ProfileScreen

// TODO KDoc
// TODO Android test
internal fun NavGraphBuilder.menuNavigationGraph(navController: NavController) {
    navigation(
        route = Graph.MENU,
        startDestination = Route.MENU
    ) {
        composable(
            route = Route.MENU,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(
                        delayMillis = 100,
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            },
            popEnterTransition = {
                val initialRoute = this.initialState.destination.route
                when (initialRoute) {
                    Route.HOME, Route.DISCOVER -> slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(
                            delayMillis = 100,
                            durationMillis = 300,
                            easing = LinearEasing
                        )
                    )
                    else -> slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(
                            delayMillis = 100,
                            durationMillis = 300,
                            easing = LinearEasing
                        )
                    )
                }
            },
            exitTransition = {
                val targetRoute = this.targetState.destination.route
                when (targetRoute) {
                    Route.HOME, Route.DISCOVER -> slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.End,
                        animationSpec = tween(
                            delayMillis = 100,
                            durationMillis = 300,
                            easing = LinearEasing
                        )
                    )
                    else -> slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Start,
                        animationSpec = tween(
                            delayMillis = 100,
                            durationMillis = 300,
                            easing = LinearEasing
                        )
                    )
                }
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(
                        delayMillis = 100,
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            }
        ) {
            MainScreen(
                toProfile = {
                    navController.navigate(Route.PROFILE)
                },
                toPrivacy = {
                    navController.navigate(Route.PRIVACY)
                },

                // TODO Implement navigation to (settings, about, support)
                toSettings = {},
                toAbout = {},
                toSupport = {}
            )
        }
        composable(
            route = Route.PROFILE,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(
                        delayMillis = 100,
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(
                        delayMillis = 100,
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            }
        ) {
            ProfileScreen(
                toBack = {
                    navController.popBackStack()
                },

                // TODO Implement navigation to (name&surname, email, password, gender, country)
                toNameSurname = {},
                toEmail = {},
                toPassword = {},
                toGender = {},
                toCountry = {}
            )
        }
        composable(
            route = Route.PRIVACY,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(
                        delayMillis = 100,
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(
                        delayMillis = 100,
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            }
        ) {
            PrivacyScreen(
                toBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
