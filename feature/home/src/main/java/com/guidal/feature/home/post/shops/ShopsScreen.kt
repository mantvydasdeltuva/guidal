package com.guidal.feature.home.post.shops


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.guidal.core.ui.components.MenuButton
import com.guidal.core.ui.models.UiModelMenuButtonIcon
import com.guidal.core.ui.theme.GuidalIcons
import com.guidal.feature.home.R

@Composable
fun ShopsScreen(
    modifier: Modifier = Modifier
) {

    val urlSklavenitisWebsite = ""

    Column(
    ) {
        Text(
            text = stringResource(R.string.shops_introduction),
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge
        )

        // Sklavenitis
        Text(
            text = stringResource(R.string.shops_sklavenitis_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = stringResource(R.string.shops_sklavenitis_text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        MenuButton(
            label = stringResource(R.string.title_official_website),
            onClick = {
            },
            trailingIcon = UiModelMenuButtonIcon(
                imageVector = GuidalIcons.Outlined.Redirect,
                size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_20)
            )
        )

        // Alfa-Beta Vassilopoulos (AB)
        Text(
            text = stringResource(R.string.shops_ab_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = stringResource(R.string.shops_ab_text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        MenuButton(
            label = stringResource(R.string.title_official_website),
            onClick = {
            },
            trailingIcon = UiModelMenuButtonIcon(
                imageVector = GuidalIcons.Outlined.Redirect,
                size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_20)
            )
        )

        // Lidl
        Text(
            text = stringResource(R.string.shops_lidl_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = stringResource(R.string.shops_lidl_text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        MenuButton(
            label = stringResource(R.string.title_official_website),
            onClick = {
            },
            trailingIcon = UiModelMenuButtonIcon(
                imageVector = GuidalIcons.Outlined.Redirect,
                size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_20)
            )
        )

        // My Market
        Text(
            text = stringResource(R.string.shops_mymarket_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = stringResource(R.string.shops_mymarket_text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        MenuButton(
            label = stringResource(R.string.title_official_website),
            onClick = {
            },
            trailingIcon = UiModelMenuButtonIcon(
                imageVector = GuidalIcons.Outlined.Redirect,
                size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_20)
            )
        )

        // Masoutis
        Text(
            text = stringResource(R.string.shops_masoutis_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = stringResource(R.string.shops_masoutis_text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        MenuButton(
            label = stringResource(R.string.title_official_website),
            onClick = {
            },
            trailingIcon = UiModelMenuButtonIcon(
                imageVector = GuidalIcons.Outlined.Redirect,
                size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_20)
            )
        )

    }

}