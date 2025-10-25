package com.islamicapp.siraathalmusthaqeem.screen.prayertimes

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import com.islamicapp.domain.prayertimes.model.PrayerTimes
import com.islamicapp.siraathalmusthaqeem.screen.prayertimes.components.PrayerTimesItem
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import com.islamicapp.siraathalmusthaqeem.utils.SpinKitLoader
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun PrayerTimesScreen(
    navController: NavController,
    viewModel: PrayerTimesViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    val uiState by viewModel.uiState.collectAsState(initial = UIState.Loading())

    var hasLocationPermission by remember { mutableStateOf(false) }
    var locationEnabled by remember { mutableStateOf(false) }
    var locationFetched by remember { mutableStateOf(false) }
    var permissionRequested by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasLocationPermission = granted
            if (granted) {
                locationEnabled = isLocationEnabled(locationManager)
                if (locationEnabled) {
                    fetchAndLoadPrayerTimes(fusedLocationClient, viewModel) {
                        locationFetched = true
                    }
                }
            }
        }
    )

    // Initial permission check
    LaunchedEffect(Unit) {
        hasLocationPermission =
            ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        locationEnabled = isLocationEnabled(locationManager)

        when {
            !hasLocationPermission && !permissionRequested -> {
                permissionRequested = true
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            hasLocationPermission && locationEnabled -> {
                fetchAndLoadPrayerTimes(fusedLocationClient, viewModel) {
                    locationFetched = true
                }
            }
        }
    }

    // Real-time GPS state observer (polls every 2s)
    LaunchedEffect(hasLocationPermission) {
        if (hasLocationPermission) {
            while (true) {
                delay(2000)
                val isEnabledNow = isLocationEnabled(locationManager)

                // User turned ON GPS
                if (isEnabledNow && !locationEnabled) {
                    locationEnabled = true
                    fetchAndLoadPrayerTimes(fusedLocationClient, viewModel) {
                        locationFetched = true
                    }
                }

                // User turned OFF GPS
                if (!isEnabledNow && locationEnabled) {
                    locationEnabled = false
                    locationFetched = false
                }
            }
        }
    }

    Scaffold(
        topBar = { CommonActionBar(title = "Prayer Times", navController = navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
                .padding(paddingValues)
        ) {
            when {
                // No permission
                !hasLocationPermission -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Please grant location permission to fetch prayer times.",
                            color = Color(0xFF374151),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                        ) {
                            Text("Grant Permission", color = Color.White)
                        }
                    }
                }

                // GPS off
                hasLocationPermission && !locationEnabled -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Location services are turned off.\nPlease enable GPS to fetch prayer times.",
                            color = Color(0xFF374151),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = {
                                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                        ) {
                            Text("Enable Location", color = Color.White)
                        }
                    }
                }

                // Loading
                uiState is UIState.Loading<*> -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        SpinKitLoader()
                    }
                }

                // Success
                uiState is UIState.Success<*> -> {
                    val prayerTimes = (uiState as UIState.Success<PrayerTimes>).data
                    PrayerTimesItem(prayerTimes)
                }

                // Empty
                uiState is UIState.Empty<*> -> {
                    Text(
                        text = "No data available",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Gray
                    )
                }

                // Error
                uiState is UIState.Failure<*> -> {
                    val errorMessage = (uiState as UIState.Failure).throwable.message
                    Text(
                        text = errorMessage ?: "Error occurred",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

// Check if GPS is ON
private fun isLocationEnabled(locationManager: LocationManager): Boolean {
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

// Fetch location + load timings
@SuppressLint("MissingPermission")
private fun fetchAndLoadPrayerTimes(
    fusedLocationClient: com.google.android.gms.location.FusedLocationProviderClient,
    viewModel: PrayerTimesViewModel,
    onFetched: () -> Unit
) {
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            onFetched()
            viewModel.getPrayerTimes("", location.latitude, location.longitude)
        } else {
            // fallback retry if GPS not yet warm
            fusedLocationClient.getCurrentLocation(
                com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).addOnSuccessListener { loc ->
                if (loc != null) {
                    onFetched()
                    viewModel.getPrayerTimes("", loc.latitude, loc.longitude)
                }
            }
        }
    }
}
