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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import com.islamicapp.domain.prayertimes.model.PrayerTimes
import com.islamicapp.siraathalmusthaqeem.screen.prayertimes.components.PrayerTimesItem
import com.islamicapp.siraathalmusthaqeem.utils.SpinKitLoader
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import com.islamicapp.siraathalmusthaqeem.utils.NoInternetScreen
import com.islamicapp.core.utils.isNetworkAvailable
import com.islamicapp.siraathalmusthaqeem.R
import com.islamicapp.siraathalmusthaqeem.utils.AsrSchoolMapper
import com.islamicapp.siraathalmusthaqeem.utils.LocationHelper
import com.islamicapp.siraathalmusthaqeem.utils.PrayerMethodMapper
import com.islamicapp.siraathalmusthaqeem.utils.PreferencesManager
import kotlinx.coroutines.runBlocking
import org.koin.compose.koinInject
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun PrayerTimesScreen(
    navController: NavController,
    viewModel: PrayerTimesViewModel = koinViewModel(),
    preferencesManager: PreferencesManager = koinInject()
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    val uiState by viewModel.uiState.collectAsState(initial = UIState.Loading())

    var hasLocationPermission by remember { mutableStateOf(false) }
    var locationEnabled by remember { mutableStateOf(false) }
    var networkAvailable by remember { mutableStateOf(isNetworkAvailable(context)) }
    var locationFetched by remember { mutableStateOf(false) }
    var permissionRequested by remember { mutableStateOf(false) }

    // Prayer settings state
    var currentMethod by remember { mutableIntStateOf(3) }
    var currentSchool by remember { mutableIntStateOf(0) }
    var currentLatitude by remember { mutableDoubleStateOf(0.0) }
    var currentLongitude by remember { mutableDoubleStateOf(0.0) }
    var currentTimezone by remember { mutableStateOf("") }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasLocationPermission = granted
            if (granted) {
                locationEnabled = isLocationEnabled(locationManager)
                networkAvailable = isNetworkAvailable(context)
                if (locationEnabled && networkAvailable) {
                    fetchAndLoadPrayerTimes(
                        fusedLocationClient,
                        viewModel,
                        context,
                        preferencesManager
                    ) { method, school, lat, long, timezone ->
                        currentMethod = method
                        currentSchool = school
                        currentLatitude = lat
                        currentLongitude = long
                        currentTimezone = timezone
                        locationFetched = true
                    }
                }
            }
        }
    )

    // Listen for updates from Settings screen
    LaunchedEffect(navController) {
        val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

        savedStateHandle?.getStateFlow<Int?>("updated_method", null)?.collect { method ->
            method?.let {
                currentMethod = it
                savedStateHandle.remove<Int>("updated_method")
                // Re-fetch prayer times with new settings
                if (currentLatitude != 0.0) {
                    viewModel.getPrayerTimes(
                        "",
                        currentLatitude,
                        currentLongitude,
                        currentMethod,
                        currentSchool
                    )
                }
            }
        }
    }

    LaunchedEffect(navController) {
        val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

        savedStateHandle?.getStateFlow<Int?>("updated_school", null)?.collect { school ->
            school?.let {
                currentSchool = it
                savedStateHandle.remove<Int>("updated_school")
                // Re-fetch prayer times with new settings
                if (currentLatitude != 0.0) {
                    viewModel.getPrayerTimes(
                        "",
                        currentLatitude,
                        currentLongitude,
                        currentMethod,
                        currentSchool
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        hasLocationPermission =
            ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        if (!hasLocationPermission && !permissionRequested) {
            permissionRequested = true
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        while (true) {
            val isNetworkOk = isNetworkAvailable(context)
            val isLocationOn = isLocationEnabled(locationManager)

            networkAvailable = isNetworkOk
            locationEnabled = isLocationOn

            if (hasLocationPermission && isLocationOn && isNetworkOk) {
                if (!locationFetched) {
                    fetchAndLoadPrayerTimes(
                        fusedLocationClient,
                        viewModel,
                        context,
                        preferencesManager
                    ) { method, school, lat, long, timezone ->
                        currentMethod = method
                        currentSchool = school
                        currentLatitude = lat
                        currentLongitude = long
                        currentTimezone = timezone
                        locationFetched = true
                    }
                }
            } else {
                locationFetched = false
            }

            delay(2000)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Prayer Times") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    // Settings Icon
                    IconButton(
                        onClick = {
                            val encodedTimezone = URLEncoder.encode(currentTimezone, StandardCharsets.UTF_8.toString())
                            navController.navigate(
                                "prayer_settings/$currentMethod/$currentSchool/$currentLatitude/$currentLongitude/$encodedTimezone"
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.green),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
                .padding(paddingValues)
        ) {
            when {
                !networkAvailable -> {
                    NoInternetScreen(
                        modifier = Modifier.align(Alignment.Center),
                        onTryAgain = {
                            context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                        }
                    )
                }

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

                uiState is UIState.Loading<*> -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        SpinKitLoader()
                    }
                }

                uiState is UIState.Success<*> -> {
                    val prayerTimes = (uiState as UIState.Success<PrayerTimes>).data
                    PrayerTimesItem(prayerTimes)
                }

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

private fun isLocationEnabled(locationManager: LocationManager): Boolean {
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

@SuppressLint("MissingPermission")
private fun fetchAndLoadPrayerTimes(
    fusedLocationClient: com.google.android.gms.location.FusedLocationProviderClient,
    viewModel: PrayerTimesViewModel,
    context: Context,
    preferencesManager: PreferencesManager,
    onFetched: (method: Int, school: Int, lat: Double, long: Double, timezone: String) -> Unit
) {
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            val countryCode = LocationHelper.getCountryFromLocation(
                context,
                location.latitude,
                location.longitude
            )

            val method = runBlocking { preferencesManager.getCalculationMethod() }
                ?: PrayerMethodMapper.getMethodForCountry(countryCode)

            val school = runBlocking { preferencesManager.getAsrSchool() }
                ?: AsrSchoolMapper.getDefaultSchoolForCountry(countryCode)

            // Note: Timezone will come from API response, using placeholder for now
            onFetched(method, school, location.latitude, location.longitude, "Asia/Kolkata")
            viewModel.getPrayerTimes("", location.latitude, location.longitude, method, school)
        } else {
            fusedLocationClient.getCurrentLocation(
                com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).addOnSuccessListener { loc ->
                if (loc != null) {
                    val countryCode = LocationHelper.getCountryFromLocation(
                        context,
                        loc.latitude,
                        loc.longitude
                    )

                    val method = runBlocking { preferencesManager.getCalculationMethod() }
                        ?: PrayerMethodMapper.getMethodForCountry(countryCode)

                    val school = runBlocking { preferencesManager.getAsrSchool() }
                        ?: AsrSchoolMapper.getDefaultSchoolForCountry(countryCode)

                    onFetched(method, school, loc.latitude, loc.longitude, "Asia/Kolkata")
                    viewModel.getPrayerTimes("", loc.latitude, loc.longitude, method, school)
                }
            }
        }
    }
}