package com.guidal.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.guidal.core.ui.R

/**
 *  * Module :core:ui
 *
 * Object `GuidalColors` serves as a centralized repository for managing app-specific colors across different themes.
 *
 * - **UniversalColors:** Colors that are used universally, regardless of theme.
 * - **LightThemeColors:** Colors specific to the light theme.
 * - **DarkThemeColors:** Colors specific to the dark theme.
 * - **Preload Function:** Initializes the color properties with resources from the app's color definitions.
 *
 * **UniversalColors**
 *   - `primary`: The primary color for the app.
 *   - `onPrimary`: The color used for text/icons displayed on top of the primary color.
 *   - `error`: The color used for errors.
 *   - `onError`: The color used for text/icons displayed on top of the error color.
 *
 * **LightThemeColors**
 *   - `surfaceDim`: A dimmed surface color.
 *   - `surface`: The standard surface color.
 *   - `surfaceBright`: A bright surface color.
 *   - `onSurface`: The color used for text/icons on surfaces.
 *   - `onSurfaceVariant`: A variant of the `onSurface` color for secondary elements.
 *
 * **DarkThemeColors**
 *   - Same as LightThemeColors but adapted for dark mode.
 *
 * **Initialization**
 *
 * The `Preload` function must be called before accessing the color properties. This function:
 * - Loads color resources using `colorResource`.
 * - Assigns them to the respective color data classes for Universal, Light, and Dark themes.
 *
 * **Best Practices**
 * - Ensure that the color resources (`R.color.*`) used in `Preload` exist in your project to avoid runtime crashes.
 * - Call `GuidalColors.Preload()` at the app's startup or theme initialization to ensure all color properties are properly set.
 * - Access colors through `GuidalColors.Universal`, `GuidalColors.Light`, or `GuidalColors.Dark` as needed.
 */
// TODO Android test
object GuidalColors {
    data class UniversalColors(
        val primary: Color,
        val onPrimary: Color,
        val error: Color,
        val onError: Color
    )

    data class LightThemeColors(
        val surfaceDim: Color,
        val surface: Color,
        val surfaceBright: Color,
        val onSurface: Color,
        val onSurfaceVariant: Color
    )

    data class DarkThemeColors(
        val surfaceDim: Color,
        val surface: Color,
        val surfaceBright: Color,
        val onSurface: Color,
        val onSurfaceVariant: Color
    )

    lateinit var Universal: UniversalColors
    lateinit var Light: LightThemeColors
    lateinit var Dark: DarkThemeColors

    @Composable
    internal fun Preload() {
        Universal = UniversalColors(
            primary = colorResource(id = R.color.primary),
            onPrimary = colorResource(id = R.color.onPrimary),
            error = colorResource(id = R.color.error),
            onError = colorResource(id = R.color.onError)
        )

        Light = LightThemeColors(
            surfaceDim = colorResource(id = R.color.lightThemeSurfaceDim),
            surface = colorResource(id = R.color.lightThemeSurface),
            surfaceBright = colorResource(id = R.color.lightThemeSurfaceBright),
            onSurface = colorResource(id = R.color.lightThemeOnSurface),
            onSurfaceVariant = colorResource(id = R.color.lightThemeOnSurfaceVariant)
        )

        Dark = DarkThemeColors(
            surfaceDim = colorResource(id = R.color.darkThemeSurfaceDim),
            surface = colorResource(id = R.color.darkThemeSurface),
            surfaceBright = colorResource(id = R.color.darkThemeSurfaceBright),
            onSurface = colorResource(id = R.color.darkThemeOnSurface),
            onSurfaceVariant = colorResource(id = R.color.darkThemeOnSurfaceVariant)
        )
    }
}