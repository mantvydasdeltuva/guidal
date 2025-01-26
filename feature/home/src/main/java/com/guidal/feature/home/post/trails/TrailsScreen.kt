package com.guidal.feature.home.post.trails


import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.guidal.core.ui.components.MenuButton
import com.guidal.core.ui.models.UiModelMenuButtonIcon
import com.guidal.core.ui.theme.GuidalIcons
import com.guidal.feature.home.R

@Composable
fun TrailsScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val urlNational031Website = "https://www.trailpath.gr/en/national-paths-greece/"
    val urlPatrasKalavrytaWebsite = "https://www.alltrails.com/trail/greece/western-greece--3/patra-ano-vlasia-agios-taxiarchis-waterfalls-kalavryta"
    val urlGirokomeioPsarthiWebsite = "https://www.alltrails.com/greece/west-greece/patras"

    Column(
    ) {
        Text(
            text = stringResource(R.string.trails_introduction),
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge
        )

        // Girokomeio Patras - Psarthi Refuge
        Text(
            text = stringResource(R.string.trails_girokomeio_psarthi_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = stringResource(R.string.trails_girokomeio_psarthi_text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        MenuButton(
            label = stringResource(R.string.title_learn_more),
            onClick = {
                openLink(context, urlGirokomeioPsarthiWebsite)
            },
            trailingIcon = UiModelMenuButtonIcon(
                imageVector = GuidalIcons.Outlined.Redirect,
                size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_20)
            )
        )

        // Patra - Ano Vlasia - Agios Taxiarchis Waterfalls - Kalavryta
        Text(
            text = stringResource(R.string.trails_patras_kalavryta_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = stringResource(R.string.trails_patras_kalavryta_text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        MenuButton(
            label = stringResource(R.string.title_learn_more),
            onClick = {
                openLink(context, urlPatrasKalavrytaWebsite)
            },
            trailingIcon = UiModelMenuButtonIcon(
                imageVector = GuidalIcons.Outlined.Redirect,
                size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_20)
            )
        )

        // National Path O31
        Text(
            text = stringResource(R.string.trails_national031_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = stringResource(R.string.trails_national031_text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        MenuButton(
            label = stringResource(R.string.title_learn_more),
            onClick = {
                openLink(context, urlNational031Website)
            },
            trailingIcon = UiModelMenuButtonIcon(
                imageVector = GuidalIcons.Outlined.Redirect,
                size = dimensionResource(com.guidal.core.ui.R.dimen.icon_size_20)
            )
        )
    }
}

fun openLink(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}