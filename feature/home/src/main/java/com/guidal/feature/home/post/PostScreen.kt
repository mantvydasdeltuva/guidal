package com.guidal.feature.home.post

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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.guidal.core.ui.R
import com.guidal.core.ui.components.Scaffold
import com.guidal.core.ui.components.TopAppBar
import com.guidal.core.ui.models.UiModelTopAppBarIcon
import com.guidal.core.ui.theme.GuidalIcons

@Composable
fun PostScreen(
    postType: String,
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
    // temporary
    val splashImage = when (postType) {
        "trails" -> R.drawable.trails
        "shops" -> R.drawable.shops
        "transportation" -> R.drawable.transporation
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
                    .padding(top = imageHeight)
                    .background(color = MaterialTheme.colorScheme.surfaceBright)
            ) {
                // Spacer to push content down (size of TopBar covering the content)
                Spacer(modifier = Modifier.height(topBarHeight/2))

                // TODO: SAMPLE DATA, REPLACE WITH DATA FROM DATABASE
                repeat(10) {
                    Text(
                        text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }

            TopAppBar(
                title = postType.replaceFirstChar { it.uppercase() },
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
                        val originalOffset = (imageHeight-topBarHeight/2).toPx()
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