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
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.guidal.data.db.models.LocationModel
import com.guidal.feature.discover.R
import kotlinx.coroutines.delay

@Composable
fun MainScreen(
    toLocationView: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val uiState by mainViewModel.uiState.collectAsState()

    // Extract locations from UI state
    val locations = remember(uiState) {
        (uiState as? MainUiState.Idle)?.locations ?: emptyList()
    }

    // Convert to marker positions
    val markerPositions = remember(locations) {
        locations.map { location ->
            LatLng(location.latitude.toDouble(), location.longitude.toDouble()) to location
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        // Start with a null position, will update after locations load
        position = CameraPosition.fromLatLngZoom(
            LatLng(0.0, 0.0),
            1f
        )
    }

    var selectedLocation by remember { mutableStateOf<LocationModel?>(null) }

    LaunchedEffect(Unit) {
        mainViewModel.fetch()
    }

    LaunchedEffect(markerPositions) {
        markerPositions.firstOrNull()?.first?.let { firstLatLng ->
            cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(firstLatLng, 14f))
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = true)
    ) {
        markerPositions.forEach { (latLng, location) ->
            val isSelected = location.id == selectedLocation?.id
            Marker(
                state = rememberMarkerState(position = latLng),
                title = location.title,
                alpha = if (selectedLocation == null || isSelected) 1f else 0.2f, // Dim non-selected markers
                onClick = {
                    cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                    selectedLocation = location
                    true
                }
            )
        }
    }

    LocationPopup(
        selectedLocation = selectedLocation,
        onDismiss = { selectedLocation = null },
        toLocationView = toLocationView
    )
}

@Composable
fun LocationPopup(
    selectedLocation: LocationModel?,
    onDismiss: () -> Unit,
    toLocationView: (Int) -> Unit
) {
    val context = LocalContext.current
    var visible by remember { mutableStateOf(false) }

    fun navigateToGoogleMaps() {
        selectedLocation?.let { location ->
            val gmmIntentUri =
                Uri.parse("google.navigation:q=${location.latitude},${location.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                setPackage("com.google.android.apps.maps")
            }
            context.startActivity(mapIntent)
        }
    }

    // Set visibility when selectedLocation changes
    LaunchedEffect(selectedLocation) {
        visible = selectedLocation != null
    }

    // Delay dismissal to prevent flicker
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
                    detectTapGestures(onTap = { visible = false })
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
                selectedLocation?.let { location ->
                    Text(text = location.title, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = location.category,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = location.address)
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            label = stringResource(R.string.location_view_title_learn_more),
                            onClick = { toLocationView(location.id) },
                            modifier = Modifier.weight(1f)
                        )

                        OutlinedButton(
                            label = stringResource(R.string.location_view_title_navigate),
                            onClick = { navigateToGoogleMaps() },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
