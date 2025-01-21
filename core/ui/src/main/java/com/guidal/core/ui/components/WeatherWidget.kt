package com.guidal.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guidal.core.ui.R
import com.guidal.core.ui.models.UiModelWeatherWidgetItem
import com.guidal.core.ui.models.WeatherType
import com.guidal.core.ui.theme.GuidalTheme

@Composable
fun WeatherWidget(
    items: List<UiModelWeatherWidgetItem>,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    getDayShortTitle: (String) -> String
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(
                if (interactionSource.collectIsPressedAsState().value && enabled)
                    MaterialTheme.colorScheme.surface
                else MaterialTheme.colorScheme.surfaceBright
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Button,
                onClick = { if (enabled) onClick() }
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
            items.forEachIndexed { index, indicator ->
                if (index == 0) {
                    Icon(
                        imageVector = indicator.type.icon,
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.icon_size_72))
                    )

                    Text(
                        text = "${indicator.value}°",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    WeatherIndicator(
                        day = indicator.day,
                        value = indicator.value,
                        type = indicator.type,
                        getDayShortTitle = getDayShortTitle
                    )
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
private fun WeatherIndicator(
    day: String,
    value: Int,
    type: WeatherType,
    modifier: Modifier = Modifier,
    getDayShortTitle: (String) -> String
){
    Column(
        modifier = modifier
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = getDayShortTitle(day),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Icon(
            imageVector = type.icon,
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(dimensionResource(R.dimen.icon_size_24))
        )

        Text(
            text = "${value}°",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewWeatherIndicator() {
    GuidalTheme {
        WeatherIndicator(
            day = "Mon",
            value = 15,
            type = WeatherType.Sunny,
            getDayShortTitle = { _ -> "" }
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewWeatherWidget() {
    WeatherWidget(
        items = listOf(
            UiModelWeatherWidgetItem(
                day = "Sun",
                value = 16,
                type = WeatherType.Rainy
            ),
            UiModelWeatherWidgetItem(
                day = "Mon",
                value = 13,
                type = WeatherType.Rainy
            ),
            UiModelWeatherWidgetItem(
                day = "Tue",
                value = 25,
                type = WeatherType.Sunny
            ),
            UiModelWeatherWidgetItem(
                day = "Wed",
                value = 21,
                type = WeatherType.PartlyCloudy
            ),
            UiModelWeatherWidgetItem(
                day = "Thu",
                value = 22,
                type = WeatherType.Sunny
            ),
            UiModelWeatherWidgetItem(
                day = "Fri",
                value = 15,
                type = WeatherType.Cloudy
            ),
            UiModelWeatherWidgetItem(
                day = "Sat",
                value = 18,
                type = WeatherType.Thunder
            )
        ),
        onClick = {},
        getDayShortTitle = { _ -> "" }
    )
}