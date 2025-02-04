package com.guidal.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.guidal.core.ui.R

/**
 * Module :core:ui
 *
 * Provides a collection of icons for the Guidal application, including both default and outlined icon sets.
 *
 * - **DefaultIcons**: Contains the primary icons used throughout the app, such as the Guidal logo, Google logo, and navigation icons.
 * - **OutlinedIcons**: Contains outlined versions of commonly used icons such as Home, Discover, Menu, and Person.
 * - **CategoryIcons**: Contains icons used for categories, such as Commute, Shop, Restaurant and more.
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
        val GuidalInline: ImageVector,
        val Google: ImageVector,
        val Home: ImageVector,
        val Discover: ImageVector,
        val Menu: ImageVector,
        val ChevronForward: ImageVector,
        val Search: ImageVector,
        val ArrowBack: ImageVector,
        val ArrowForward: ImageVector,
        val Favorite: ImageVector,
        val Star: ImageVector,
        val StarHalf: ImageVector,
        val Map: ImageVector,
        val Location: ImageVector,
        val MapPoint: ImageVector
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
        val Redirect: ImageVector,
        val Favorite: ImageVector,
        val Star: ImageVector,
        val PriceTag: ImageVector
    )

    data class CategoryIcons(
        val Museum: ImageVector,
        val Beach: ImageVector,
        val Commute: ImageVector,
        val Hiking: ImageVector,
        val NightLife: ImageVector,
        val Restaurant: ImageVector,
        val Shop: ImageVector,
        val CircledStar: ImageVector,
        val Favorite: ImageVector,
    )

    data class CountryIcons(
        val Other: ImageVector,
        val Greece: ImageVector,
        val Lithuania: ImageVector
    )

    data class GenderIcons(
        val Other: ImageVector,
        val Man: ImageVector,
        val Woman: ImageVector
    )

    data class WeatherIcons(
        val Sunny: ImageVector,
        val Cloudy: ImageVector,
        val PartlyCloudy: ImageVector,
        val Rainy: ImageVector,
        val Thunder: ImageVector,
        val Snowy: ImageVector
    )

    lateinit var Default: DefaultIcons
    lateinit var Outlined: OutlinedIcons
    lateinit var Category: CategoryIcons
    lateinit var Country: CountryIcons
    lateinit var Gender: GenderIcons
    lateinit var Weather: WeatherIcons

    @Composable
    internal fun Preload() {
        Default = DefaultIcons(
            Guidal = ImageVector.vectorResource(R.drawable.guidal_icon),
            GuidalLogo = ImageVector.vectorResource(R.drawable.guidal_logo_icon),
            GuidalInline = ImageVector.vectorResource(R.drawable.guidal_inline_icon),
            Google = ImageVector.vectorResource(R.drawable.google_logo_icon),
            Home = ImageVector.vectorResource(R.drawable.home_icon),
            Discover = ImageVector.vectorResource(R.drawable.discover_icon),
            Menu = ImageVector.vectorResource(R.drawable.menu_icon),
            ChevronForward = ImageVector.vectorResource(R.drawable.chevron_forward_icon),
            Search = ImageVector.vectorResource(R.drawable.search_icon),
            ArrowBack = ImageVector.vectorResource(R.drawable.arrow_back_icon),
            ArrowForward = ImageVector.vectorResource(R.drawable.arrow_forward_icon),

            Favorite = ImageVector.vectorResource(R.drawable.favorite_icon),
            Star = ImageVector.vectorResource(R.drawable.star_icon),
            StarHalf = ImageVector.vectorResource(R.drawable.star_half_icon),
            Map = ImageVector.vectorResource(R.drawable.map_icon),
            Location = ImageVector.vectorResource(R.drawable.location_icon),

            MapPoint = ImageVector.vectorResource(R.drawable.location_map_point_icon),
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
            Redirect = ImageVector.vectorResource(R.drawable.redirect_outlined_icon),

            Favorite = ImageVector.vectorResource(R.drawable.favorite_outlined_icon),
            Star = ImageVector.vectorResource(R.drawable.star_outlined_icon),
            PriceTag = ImageVector.vectorResource(R.drawable.price_tag_outlined_icon)
        )

        Category = CategoryIcons(
            Museum = ImageVector.vectorResource(R.drawable.museum_icon),
            Beach = ImageVector.vectorResource(R.drawable.beach_icon),
            Commute = ImageVector.vectorResource(R.drawable.commute_icon),
            Hiking = ImageVector.vectorResource(R.drawable.hiking_icon),
            NightLife = ImageVector.vectorResource(R.drawable.nightlife_icon),
            Restaurant = ImageVector.vectorResource(R.drawable.restaurant_icon),
            Shop = ImageVector.vectorResource(R.drawable.shop_icon),
            CircledStar = ImageVector.vectorResource(R.drawable.circled_star_icon),
            Favorite = ImageVector.vectorResource(R.drawable.favorite_outlined_icon),
        )

        Country = CountryIcons(
            Other = ImageVector.vectorResource(R.drawable.language_other_icon),
            Greece = ImageVector.vectorResource(R.drawable.language_greece_icon),
            Lithuania = ImageVector.vectorResource(R.drawable.language_lithuania_icon)
        )

        Gender = GenderIcons(
            Other = ImageVector.vectorResource(R.drawable.gender_other_icon),
            Man = ImageVector.vectorResource(R.drawable.gender_man_icon),
            Woman = ImageVector.vectorResource(R.drawable.gender_woman_icon)
        )

        Weather = WeatherIcons(
            Sunny = ImageVector.vectorResource(R.drawable.weather_sunny_icon),
            Cloudy = ImageVector.vectorResource(R.drawable.weather_cloudy_icon),
            PartlyCloudy = ImageVector.vectorResource(R.drawable.weather_partlycloudy_icon),
            Rainy = ImageVector.vectorResource(R.drawable.weather_rainy_icon),
            Thunder = ImageVector.vectorResource(R.drawable.weather_thunder_icon),
            Snowy = ImageVector.vectorResource(R.drawable.weather_snowy_icon)
        )
    }
}