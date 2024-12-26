package com.guidal.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.guidal.core.ui.R
import org.intellij.lang.annotations.Language

/**
 * Module :core:ui
 *
 * Provides a collection of icons for the Guidal application, including both default and outlined icon sets.
 *
 * - **DefaultIcons**: Contains the primary icons used throughout the app, such as the Guidal logo, Google logo, and navigation icons.
 * - **OutlinedIcons**: Contains outlined versions of commonly used icons such as Home, Discover, Menu, and Person.
 *
 * **Initialization**
 *
 * The `Preload` function must be called before accessing icons.
 *
 * **Best Practices**
 * - Call `GuidalIcons.Preload()` early in the app lifecycle to preload icons for immediate use.
 * - Use the `Default` icon set for the main icons and `Outlined` icon set for alternate or secondary icon representations.
 */
// TODO Android test
object GuidalIcons {
    data class DefaultIcons(
        val Guidal: ImageVector,
        val GuidalLogo: ImageVector,
        val Google: ImageVector,
        val Home: ImageVector,
        val Discover: ImageVector,
        val Menu: ImageVector,
        val ChevronForward: ImageVector,
        val Search: ImageVector,
        val ArrowBack: ImageVector,
        val ArrowForward: ImageVector
    )

    data class OutlinedIcons(
        val Home: ImageVector,
        val Discover: ImageVector,
        val Menu: ImageVector,
        val Profile: ImageVector,
        val Settings: ImageVector,
        val Privacy: ImageVector,
        val About: ImageVector,
        val Support: ImageVector,
        val Id: ImageVector,
        val Email: ImageVector,
        val Password: ImageVector,
        val Delete: ImageVector,
        val Edit: ImageVector,
        val Redirect: ImageVector
    )

    data class LanguageIcons(
        val Other: ImageVector,
        val Greece: ImageVector,
        val Lithuania: ImageVector
    )

    data class GenderIcons(
        val Other: ImageVector,
        val Man: ImageVector,
        val Woman: ImageVector
    )

    lateinit var Default: DefaultIcons
    lateinit var Outlined: OutlinedIcons
    lateinit var Language: LanguageIcons
    lateinit var Gender: GenderIcons

    @Composable
    internal fun Preload() {
        Default = DefaultIcons(
            Guidal = ImageVector.vectorResource(R.drawable.guidal_icon),
            GuidalLogo = ImageVector.vectorResource(R.drawable.guidal_logo_icon),
            Google = ImageVector.vectorResource(R.drawable.google_logo_icon),
            Home = ImageVector.vectorResource(R.drawable.home_icon),
            Discover = ImageVector.vectorResource(R.drawable.discover_icon),
            Menu = ImageVector.vectorResource(R.drawable.menu_icon),
            ChevronForward = ImageVector.vectorResource(R.drawable.chevron_forward_icon),
            Search = ImageVector.vectorResource(R.drawable.search_icon),
            ArrowBack = ImageVector.vectorResource(R.drawable.arrow_back_icon),
            ArrowForward = ImageVector.vectorResource(R.drawable.arrow_forward_icon)
        )

        Outlined = OutlinedIcons(
            Home = ImageVector.vectorResource(R.drawable.home_outlined_icon),
            Discover = ImageVector.vectorResource(R.drawable.discover_outlined_icon),
            Menu = ImageVector.vectorResource(R.drawable.menu_outlined_icon),
            Profile = ImageVector.vectorResource(R.drawable.profile_outlined_icon),
            Settings = ImageVector.vectorResource(R.drawable.settings_outlined_icon),
            Privacy = ImageVector.vectorResource(R.drawable.privacy_outlined_icon),
            About = ImageVector.vectorResource(R.drawable.about_outlined_icon),
            Support = ImageVector.vectorResource(R.drawable.support_outlined_icon),
            Id = ImageVector.vectorResource(R.drawable.id_outlined_icon),
            Email = ImageVector.vectorResource(R.drawable.email_outlined_icon),
            Password = ImageVector.vectorResource(R.drawable.password_outlined_icon),
            Delete = ImageVector.vectorResource(R.drawable.delete_outlined_icon),
            Edit = ImageVector.vectorResource(R.drawable.edit_outlined_icon),
            Redirect = ImageVector.vectorResource(R.drawable.redirect_outlined_icon)
        )

        Language = LanguageIcons(
            Other = ImageVector.vectorResource(R.drawable.language_other_icon),
            Greece = ImageVector.vectorResource(R.drawable.language_greece_icon),
            Lithuania = ImageVector.vectorResource(R.drawable.language_lithuania_icon)
        )

        Gender = GenderIcons(
            Other = ImageVector.vectorResource(R.drawable.gender_other_icon),
            Man = ImageVector.vectorResource(R.drawable.gender_man_icon),
            Woman = ImageVector.vectorResource(R.drawable.gender_woman_icon)
        )
    }
}