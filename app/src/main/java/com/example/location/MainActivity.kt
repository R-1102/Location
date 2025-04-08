package com.example.location

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.location.screens.GetCurrentLocation
import com.example.location.screens.MapScreen
import com.example.location.screens.fetchLocationAndAddress
import com.example.location.ui.theme.LocationTheme
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocationTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box() {
                        MapScreen()
                    }
                        BottomCard()
                    }
                }
            }
        }
    }

@Composable
fun BottomCard() {
    val context = LocalContext.current
    var address by remember { mutableStateOf("Fetching address...") }
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val offsetY =
        with(LocalDensity.current) { (screenHeight / 1.5f).toPx().toInt() }

    Box(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset { IntOffset(x = 0, y = offsetY) }
                .fillMaxSize(),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)

        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Button(
                        onClick = {
                            fetchLocationAndAddress(context, fusedLocationClient) {
                                address = it ?: "Unable to fetch address"
                            }
                        },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Update Location")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Current Location",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Location Text
                GetCurrentLocation()

            }
        }
    }
}