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
import com.islamicapp.siraathalmusthaqeem.utils.NoInternetScreen
import com.islamicapp.core.utils.isNetworkAvailable

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
    // Initialize state using the core function
    var networkAvailable by remember { mutableStateOf(isNetworkAvailable(context)) }
    var locationFetched by remember { mutableStateOf(false) }
    var permissionRequested by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasLocationPermission = granted
            if (granted) {
                // If permission granted, force re-check all statuses immediately
                locationEnabled = isLocationEnabled(locationManager)
                networkAvailable = isNetworkAvailable(context)
                if (locationEnabled && networkAvailable) {
                    fetchAndLoadPrayerTimes(fusedLocationClient, viewModel) {
                        locationFetched = true
                    }
                }
            }
        }
    )

    // Combined Status Polling and Initial Check/Fetch Logic
    LaunchedEffect(Unit) {
        // Initial permission check
        hasLocationPermission =
            ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        if (!hasLocationPermission && !permissionRequested) {
            permissionRequested = true
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        // Continuous polling loop for all statuses
        while (true) {
            val isNetworkOk = isNetworkAvailable(context)
            val isLocationOn = isLocationEnabled(locationManager)

            // 1. Update states
            networkAvailable = isNetworkOk
            locationEnabled = isLocationOn

            // 2. Attempt to fetch if ready and not fetched yet
            if (hasLocationPermission && isLocationOn && isNetworkOk) {
                if (!locationFetched) {
                    fetchAndLoadPrayerTimes(fusedLocationClient, viewModel) {
                        locationFetched = true
                    }
                }
            } else {
                locationFetched = false // Reset fetch status if any dependency is missing
            }

            delay(2000) // Poll every 2 seconds
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
                // 1. NO INTERNET (Highest Priority)
                !networkAvailable -> {
                    // Use NoInternetScreen, the button inside handles opening settings
                    NoInternetScreen(
                        modifier = Modifier.align(Alignment.Center),
                        onTryAgain = {
                            // Manual trigger to check network status immediately
                            // The LaunchedEffect loop will handle the status update shortly after
                            context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                        }
                    )
                }

                // 2. No permission
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

                // 3. GPS off
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

                // 4. Loading
                uiState is UIState.Loading<*> -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        SpinKitLoader()
                    }
                }

                // 5. Success
                uiState is UIState.Success<*> -> {
                    val prayerTimes = (uiState as UIState.Success<PrayerTimes>).data
                    PrayerTimesItem(prayerTimes)
                }

                // 6. Empty / Error
                uiState is UIState.Empty<*> -> {
                    Text(
                        text = "No data available",
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Gray
                    )
                }

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

// Check if GPS is ON (retains existing logic)
private fun isLocationEnabled(locationManager: LocationManager): Boolean {
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

// Fetch location + load timings (retains existing logic)
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