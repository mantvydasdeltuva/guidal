package com.guidal.app.graphs

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.guidal.authentication.authentication.AuthenticationScreen
import com.guidal.authentication.login.LoginScreen
import com.guidal.authentication.signup.SignupScreen

// TODO KDoc
// TODO Android test
internal fun NavGraphBuilder.authNavigationGraph(navController: NavController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = Route.AUTHENTICATION,
    ) {
        composable(
            route = Route.AUTHENTICATION,
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(
                        delayMillis = 100,
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(
                        delayMillis = 100,
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            }
        ) {
            AuthenticationScreen(
                toLogin = {
                    navController.navigate(Route.LOGIN)
                },
                toSignup = {
                    navController.navigate(Route.SIGNUP)
                },
                toHome = {
                    navController.navigate(Graph.HOME) {
                        popUpTo(Graph.AUTHENTICATION) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = Route.LOGIN,
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
            exitTransition = {
                slideOutOfContainer(
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
            LoginScreen(
                toBack = {
                    navController.popBackStack()
                },
                toHome = {
                    navController.navigate(Graph.HOME) {
                        popUpTo(Graph.AUTHENTICATION) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = Route.SIGNUP,
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
            exitTransition = {
                slideOutOfContainer(
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
            SignupScreen(
                toBack = {
                    navController.popBackStack()
                },
                toLogin = {
                    navController.navigate(Route.LOGIN) {
                        popUpTo(Route.AUTHENTICATION)
                    }
                }
            )
        }
    }
}
