package com.guidal.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Module :core:ui
 *
 * Composable function to apply the `GuidalTheme` across the app, supporting light, dark, and dynamic color schemes.
 *
 * @param darkTheme Determines if the dark theme should be applied. Defaults to the system's theme setting.
 * @param dynamicColor Enables dynamic colors on Android 12+ if `true`. Defaults to `false`.
 * @param content A composable lambda for the content to which the theme will be applied.
 * @return Applies a `MaterialTheme` with the appropriate color scheme, typography, and shapes.
 *
 * ‎
 *
 * **Best Practices**
 * - Enable `dynamicColor` for a personalized appearance on devices running Android 12+.
 * - Use this theme wrapper at the root of your app for consistent styling.
 */
// TODO Android test
@Composable
fun GuidalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    GuidalIcons.Preload()
    GuidalColors.Preload()

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme(
            primary = GuidalColors.Universal.primary,
            onPrimary = GuidalColors.Universal.onPrimary,
            error = GuidalColors.Universal.error,
            onError = GuidalColors.Universal.onError,
            surfaceDim = GuidalColors.Dark.surfaceDim,
            surface = GuidalColors.Dark.surface,
            surfaceBright = GuidalColors.Dark.surfaceBright,
            onSurface = GuidalColors.Dark.onSurface,
            onSurfaceVariant = GuidalColors.Dark.onSurfaceVariant,
        )
        else -> lightColorScheme(
            primary = GuidalColors.Universal.primary,
            onPrimary = GuidalColors.Universal.onPrimary,
            error = GuidalColors.Universal.error,
            onError = GuidalColors.Universal.onError,
            surfaceDim = GuidalColors.Light.surfaceDim,
            surface = GuidalColors.Light.surface,
            surfaceBright = GuidalColors.Light.surfaceBright,
            onSurface = GuidalColors.Light.onSurface,
            onSurfaceVariant = GuidalColors.Light.onSurfaceVariant
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = Shape,
        typography = Typography,
        content = content
    )
}