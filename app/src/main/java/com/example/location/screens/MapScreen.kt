package com.example.location.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.location.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen() {
    val context = LocalContext.current

    val aziziah = LatLng(24.420129, 39.504596)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(aziziah, 15f)
    }

    var uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = true)) }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
       properties = MapProperties(
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style_night)
        ),
        uiSettings = uiSettings
    ) {
        Marker(
            state = MarkerState(position = aziziah),
            title = "One Marker"
        )
    }
}
