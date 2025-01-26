package com.guidal.feature.home.post.shops

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.guidal.core.ui.components.HorizontalDivider
import com.guidal.core.ui.components.InTextButton
import com.guidal.feature.home.R

@Composable
fun ShopsScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val urlSklavenitisWebsite = "https://www.sklavenitis.gr/"
    val urlABWebsite = "https://www.ab.gr/"
    val urlLidlWebsite = "https://www.lidl-hellas.gr/"
    val urlMyMarketWebsite = "https://www.mymarket.gr/"
    val urlMasoutisWebsite = "https://www.masoutis.gr/"

    Column()
    {
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
        InTextButton(
            label = stringResource(R.string.title_official_website),
            onClick = {
                openLink(context, urlSklavenitisWebsite)
            },
        )
        HorizontalDivider()

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

        InTextButton(
            label = stringResource(R.string.title_official_website),
            onClick = {
                openLink(context, urlABWebsite)
            },
        )
        HorizontalDivider()

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
        InTextButton(
            label = stringResource(R.string.title_official_website),
            onClick = {
                openLink(context, urlLidlWebsite)
            },
        )
        HorizontalDivider()

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
        InTextButton(
            label = stringResource(R.string.title_official_website),
            onClick = {
                openLink(context, urlMyMarketWebsite)
            },
        )
        HorizontalDivider()

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
        InTextButton(
            label = stringResource(R.string.title_official_website),
            onClick = {
                openLink(context, urlMasoutisWebsite)
            },
        )
        HorizontalDivider()
    }
}

fun openLink(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}