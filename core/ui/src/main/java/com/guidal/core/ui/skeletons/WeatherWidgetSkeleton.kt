package com.guidal.core.ui.skeletons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guidal.core.ui.components.HorizontalDivider
import com.guidal.core.ui.modifiers.shimmerModifier
import com.guidal.core.ui.theme.GuidalTheme

@Composable
fun WeatherWidgetSkeleton(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(
                MaterialTheme.colorScheme.surfaceBright
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .shimmerModifier(
                        color = MaterialTheme.colorScheme.surface,
                        shape = CircleShape
                    ),
            )

            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .shimmerModifier(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(8.dp)
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(0.70f),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                repeat(3) {
                    WeatherIndicatorSkeleton()
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        )
    }
}

@Composable
private fun WeatherIndicatorSkeleton(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(20.dp)
            .shimmerModifier(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
    )
}

@Preview(showBackground = false)
@Composable
private fun PreviewWeatherIndicatorSkeleton() {
    GuidalTheme {
        WeatherIndicatorSkeleton()
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewWeatherWidgetSkeleton() {
    WeatherWidgetSkeleton()
}