package com.islamicapp.siraathalmusthaqeem.screen.qibla

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.koinViewModel
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QiblaScreen(
    navController: NavController,
    viewModel: QiblaViewModel = koinViewModel()
) {
    val locationPermissionState =
        rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    // Observe state
    val angle by viewModel.qiblaAngle.collectAsState()
    val qiblaImageBytes by viewModel.qiblaImage.collectAsState()

    // Request location permission and fetch API image
    LaunchedEffect(Unit) {
        if (!locationPermissionState.status.isGranted) {
            locationPermissionState.launchPermissionRequest()
        } else {
            viewModel.fetchQiblaImage()
        }
    }

    Scaffold(
        topBar = {
            CommonActionBar(title = "Qibla Direction", navController = navController)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            // Background API image centered
            qiblaImageBytes?.let { bytes ->
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Qibla Background",
                    modifier = Modifier
                        .wrapContentSize() // image will wrap its size and stay centered
                )
            }

            // Rotating arrow on top
            Image(
                painter = painterResource(id = R.drawable.ic_qibla_arrow),
                contentDescription = "Qibla Arrow",
                modifier = Modifier
                    .size(200.dp)
                    .graphicsLayer(rotationZ = angle)
            )

            // Show message if permission is not granted
            if (!locationPermissionState.status.isGranted) {
                Text(
                    text = "Location permission is required to show Qibla direction",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
