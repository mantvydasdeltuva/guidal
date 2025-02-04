package com.guidal.core.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.guidal.core.ui.R
import com.guidal.core.ui.theme.GuidalIcons

@Composable
fun RatingBar(rating: Float, maxRating: Int = 5) {
    Row {
        for (i in 1..maxRating) {
            val icon = when {
                i <= rating -> GuidalIcons.Default.Star
                i - 0.5f <= rating -> GuidalIcons.Default.StarHalf
                else -> GuidalIcons.Outlined.Star
            }

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(dimensionResource(R.dimen.icon_size_20))
            )
        }
    }
}