package com.guidal.feature.home.post.transportation

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
fun TransportationScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val urlBusWebsite = "https://www.astikopatras.gr/"
    val urlRailwayWebsite = "https://www.hellenictrain.gr/en/patras-suburban-railway"
    val urlTaxiWebsite = "https://taxipatras.com/"


    Column()
    {
        Text(
            text = stringResource(R.string.transportation_introduction),
            modifier = Modifier
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge
        )

        // Public Bus System (Astiko Patras)
        Text(
            text = stringResource(R.string.transportation_bus_system_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = stringResource(R.string.transportation_bus_system_text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = stringResource(R.string.transportation_bus_system_text_zones),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        InTextButton(
            label = stringResource(R.string.title_official_website),
            onClick = {
                openLink(context, urlBusWebsite)
            },
        )
        HorizontalDivider()

        // Suburban Railway (Proastiakos)
        Text(
            text = stringResource(R.string.transportation_railway_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = stringResource(R.string.transportation_railway_text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        InTextButton(
            label = stringResource(R.string.title_official_website),
            onClick = {
                openLink(context, urlRailwayWebsite)
            },
        )
        HorizontalDivider()

        // Taxis
        Text(
            text = stringResource(R.string.transportation_taxis_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = stringResource(R.string.transportation_taxis_text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        InTextButton(
            label = stringResource(R.string.title_learn_more),
            onClick = {
                openLink(context, urlTaxiWebsite)
            },
        )
        HorizontalDivider()

        // Cycling
        Text(
            text = stringResource(R.string.transportation_cycling_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp),
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = stringResource(R.string.transportation_cycling_text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

fun openLink(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}