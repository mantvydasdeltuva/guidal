package com.guidal.feature.discover.main

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.guidal.core.ui.components.OutlinedButton
import com.guidal.core.ui.components.Button
import com.guidal.feature.discover.R
import kotlinx.coroutines.delay

@Composable
fun MainScreen(
    toLocationView: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val markerPositions = listOf(
        LatLng(38.2466, 21.7346), // Example Marker 1
        LatLng(38.2500, 21.7350), // Example Marker 2
        LatLng(38.2400, 21.7300)  // Example Marker 3
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerPositions.firstOrNull() ?: LatLng(37.7749, -122.4194), 14f)
    }

    var selectedMarker by remember { mutableStateOf<LatLng?>(null) }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true)
    ) {
        markerPositions.forEach { latLng ->
            Marker(
                state = rememberMarkerState(position = latLng),
                title = "Location",
                onClick = {
                    cameraPositionState.move(
                        CameraUpdateFactory.newLatLngZoom(latLng, 15f) // Zoom in on click
                    )
                    selectedMarker = latLng
                    true
                }
            )
        }
    }

    LocationPopup(
        selectedMarker = selectedMarker,
        onDismiss = { selectedMarker = null },
        toLocationView = { toLocationView(0) }
    )
}

@Composable
fun LocationPopup(selectedMarker: LatLng?, onDismiss: () -> Unit, toLocationView: (Int) -> Unit) {
    var visible by remember { mutableStateOf(false) }

    val context = LocalContext.current

    fun navigateToGoogleMaps() {
        selectedMarker?.let { latLng ->
            val gmmIntentUri = Uri.parse("google.navigation:q=${latLng.latitude},${latLng.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                setPackage("com.google.android.apps.maps")
            }
            context.startActivity(mapIntent)
        }
    }

    // Set visibility when selectedMarker changes
    LaunchedEffect(selectedMarker) {
        visible = selectedMarker != null
    }

    // Delay disappearance of information, otherwise 'nulls' are visible to the user
    LaunchedEffect(visible) {
        if (!visible) {
            delay(300)
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(animationSpec = tween(300)),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(animationSpec = tween(300))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            visible = false
                        }
                    )
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .padding(24.dp)
                    .pointerInput(Unit) { detectTapGestures { } }
            ) {
                // TODO: CHANGE TO MARKER DETAILS MAPPING
                Text(text = "Location Title", style = MaterialTheme.typography.titleMedium)
                Text(text = "Category", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Address")
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        label = stringResource(R.string.location_view_title_learn_more),
                        modifier = Modifier.weight(1f),
                        onClick = { toLocationView(0) },
                    )

                    OutlinedButton(
                        onClick = { navigateToGoogleMaps() },
                        modifier = Modifier.weight(1f),
                        label = stringResource(R.string.location_view_title_navigate)
                    )
                }

            }
        }
    }
}