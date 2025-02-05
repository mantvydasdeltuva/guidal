package com.guidal.feature.home.location.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.guidal.core.ui.components.Scaffold
import com.guidal.core.ui.components.TopAppBar
import com.guidal.core.ui.models.UiModelTopAppBarIcon
import com.guidal.core.ui.theme.GuidalIcons
import com.guidal.core.ui.R
import com.guidal.core.ui.components.HorizontalDivider
import com.guidal.core.ui.components.InTextButton
import com.guidal.core.ui.components.RatingBar
import com.guidal.core.ui.models.UiModelInTextButtonIcon
import com.guidal.core.ui.skeletons.TextBlockSkeleton
import kotlinx.coroutines.launch

@Composable
fun LocationViewScreen(
    id: Int, toBack: () -> Unit, modifier: Modifier = Modifier
) {
    val locationViewViewModel: LocationViewViewModel = hiltViewModel()
    val uiState by locationViewViewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // Values, used for display and calculations
    val imageHeight = 250.dp
    val topBarHeight = 72.dp

    // Calculating corner radius based on scroll
    val maxRadius = 24.dp
    val minRadius = 0.dp

    val scrollProgress = scrollState.value.toFloat() / 550f
    val dynamicRadius = (maxRadius.value * (1 - scrollProgress)).coerceAtLeast(minRadius.value)

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        locationViewViewModel.fetch(id)
    }

    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(
                visible = scrollState.value.toFloat() >= 550f,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            scrollState.animateScrollTo(0)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                ) {
                    Icon(
                        imageVector = GuidalIcons.Default.ChevronForward,
                        contentDescription = "Scroll to Top",
                        modifier = Modifier.rotate(-90f)
                    )
                }
            }
        }, modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
            ) {
                Image(
                    painter = painterResource(
                        id = getLocationImageResId(
                            LocalContext.current, uiState.location?.id
                        )
                    ),
                    contentDescription = "Location Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(top = imageHeight) // Padding for whole component
                    .background(color = MaterialTheme.colorScheme.surfaceBright)
                    .padding(20.dp) // Padding for contents
            ) {
                Spacer(modifier = Modifier.height(topBarHeight / 2))

                when (val state = uiState) {
                    is LocationViewUiState.Loading -> {
                        repeat(8) {
                            TextBlockSkeleton()
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }

                    else -> {
                        // Description
                        Text(
                            text = "Description",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            text = uiState.location?.description ?: "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )

                        // Rating
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(vertical = 10.dp)
                        ) {
                            Text(
                                text = uiState.location?.rating.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            RatingBar(rating = uiState.location?.rating ?: 0f)
                        }
                        HorizontalDivider()

                        // Address
                        InTextButton(
                            label = uiState.location?.address ?: "Unknown Address",
                            onClick = {
                                // Open coordinates in Google Maps
                                val gmmIntentUri =
                                    Uri.parse("google.navigation:q=${uiState.location?.latitude},${uiState.location?.longitude}")
                                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                                    setPackage("com.google.android.apps.maps")
                                }
                                context.startActivity(mapIntent)
                            },
                            leadingIcon = UiModelInTextButtonIcon(
                                imageVector = GuidalIcons.Default.Location,
                            ),
                        )
                        HorizontalDivider()

                        // Price
                        InTextButton(
                            label = uiState.location?.price?.takeIf { it > 0 }?.let { price ->
                                if ((price % 1).toDouble() == 0.0) {
                                    "${price.toInt()}€"
                                } else {
                                    "$price€"
                                }
                            } ?: "Free",
                            leadingIcon = UiModelInTextButtonIcon(
                                imageVector = GuidalIcons.Outlined.PriceTag
                            ),
                            onClick = {},
                            enabled = false,
                            showNavigationIcon = false
                        )
                        HorizontalDivider()

                        // Schedule
                        InTextButton(
                            label = "Schedule",
                            leadingIcon = UiModelInTextButtonIcon(
                                imageVector = GuidalIcons.Default.Schedule
                            ),
                            onClick = {},
                            enabled = false,
                            showNavigationIcon = false
                        )
                        Text(
                            text = uiState.location?.schedule ?: "Not specified",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 15.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        HorizontalDivider()
                    }
                }
            }

            TopAppBar(title = uiState.location?.title ?: "Unknown",
                navigationIcon = UiModelTopAppBarIcon(
                    icon = GuidalIcons.Default.ArrowBack,
                    onClick = toBack,
                    color = MaterialTheme.colorScheme.onSurface,
                    size = dimensionResource(R.dimen.icon_size_16)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp)
                    .offset {
                        val scroll = scrollState.value
                        val originalOffset = (imageHeight - topBarHeight / 2).toPx()
                        val offset = (originalOffset - scroll).coerceAtLeast(0f)
                        IntOffset(x = 0, y = offset.toInt())
                    }

                    // Cannot apply RoundedCornerShape to TopStart and TopEnd
                    // On emulator it works, but on device it bugs out and is unusable
                    .clip(RoundedCornerShape(dynamicRadius.dp)))
        }
    }
}

private fun getLocationImageResId(context: Context, locationId: Int?): Int {
    // Dynamically set images to locations
    return locationId?.let { id ->
        context.resources.getIdentifier(
            "image_location_$id", // Example: image_location_5.jpg
            "drawable", context.packageName
        )
    }.takeIf { it != 0 } ?: R.drawable.sample_image
}