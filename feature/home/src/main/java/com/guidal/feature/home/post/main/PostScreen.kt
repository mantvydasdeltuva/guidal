package com.guidal.feature.home.post.main

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.guidal.core.ui.components.Scaffold
import com.guidal.core.ui.components.TopAppBar
import com.guidal.core.ui.models.UiModelTopAppBarIcon
import com.guidal.core.ui.theme.GuidalIcons
import com.guidal.core.ui.R
import com.guidal.core.ui.skeletons.TextBlockSkeleton
import com.guidal.feature.home.post.shops.ShopsScreen
import com.guidal.feature.home.post.trails.TrailsScreen
import com.guidal.feature.home.post.transportation.TransportationScreen

@Composable
fun PostScreen(
    id: Int,
    toBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val postViewModel: PostViewModel = viewModel()
    val uiState by postViewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()

    // Values, used for display and calculations
    val imageHeight = 250.dp
    val topBarHeight = 72.dp

    // Calculating corner radius based on scroll
    val maxRadius = 24.dp
    val minRadius = 0.dp

    val scrollProgress = scrollState.value.toFloat() / 550f
    val dynamicRadius = (maxRadius.value * (1 - scrollProgress)).coerceAtLeast(minRadius.value)

    // TODO: SWITCH TO FETCHING DATA FROM THE DATABASE
    // TODO: ADD TRANSLATIONS AFTER DATABASE FETCHING
    // temporary
    val splashImage = when (id) {
        3 -> R.drawable.trails
        2 -> R.drawable.shops
        1 -> R.drawable.transporation
        else -> 0
    }
    val imagePainter = if (splashImage != 0) painterResource(id = splashImage) else null

    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // TODO: REPLACE WITH IMAGES FROM THE FETCHED DATABASE
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
            ) {
                imagePainter?.let {
                    Image(
                        painter = it,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
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
                    is PostUiState.Loading -> {
                        repeat(8) {
                            TextBlockSkeleton()
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }

                    else -> {
                        // TODO: SWITCH TO DATABASE
                        when (id) {
                            1 -> TransportationScreen()
                            2 -> ShopsScreen()
                            3 -> TrailsScreen()
                            else -> {
                                Text(
                                    text = "Empty",
                                    modifier = Modifier.fillMaxSize(),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }

            TopAppBar(
                title = getPostTitle(LocalContext.current, id),
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


fun getPostTitle(context: Context, id: Int): String {
    val titleMap = mapOf(
        3 to com.guidal.feature.home.R.string.trails_title,
        2 to com.guidal.feature.home.R.string.shops_title,
        1 to com.guidal.feature.home.R.string.transportation_title
    )

    val stringResId = titleMap[id] ?: com.guidal.feature.home.R.string.post_title
    return context.getString(stringResId)
}
