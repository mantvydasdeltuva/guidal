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
import androidx.compose.ui.graphics.painter.Painter
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
    id: Int,
    toBack: () -> Unit,
    modifier: Modifier = Modifier
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
        },
        modifier = modifier
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
                            LocalContext.current,
                            uiState.location?.id
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
                        Text(
                            text = "Summary",
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
                        InTextButton(
                            label = uiState.location?.address ?: "Unknown Address",
                            onClick = {
                                // Navigate to Google Maps link, it should automatically open Google Maps app
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
                        InTextButton(
                            label = uiState.location?.price?.takeIf { it > 0 }?.toString()
                                ?: "Free",
                            leadingIcon = UiModelInTextButtonIcon(
                                imageVector = GuidalIcons.Outlined.PriceTag,
                            ),
                            onClick = {
                            },
                            enabled = false,
                            showNavigationIcon = false
                        )
                        HorizontalDivider()
                        Text(
                            text = "Detailed View",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, bottom = 10.dp),
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                                    "\n" +
                                    "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n" +
                                    "\n" +
                                    "The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            TopAppBar(
                title = uiState.location?.title ?: "Unknown",
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
                    .clip(RoundedCornerShape(dynamicRadius.dp))
            )
        }
    }
}

private fun getLocationImageResId(context: Context, locationId: Int?): Int {
    // Dynamically set images to locations
    // Usage: painter = painterResource(id = locationImageResId)
    return locationId?.let { id ->
        context.resources.getIdentifier(
            "image_location_$id", // Example: image_location_5.jpg
            "drawable",
            context.packageName
        )
    }.takeIf { it != 0 } ?: R.drawable.sample_image
}