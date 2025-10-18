package com.islamicapp.siraathalmusthaqeem.screen.qibla

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.GeomagneticField
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.islamicapp.siraathalmusthaqeem.screen.qibla.components.CompassUI
import com.islamicapp.siraathalmusthaqeem.utils.CommonActionBar
import kotlin.math.*

@Composable
fun QiblaScreen(navController: NavController) {
    val context = LocalContext.current

    var azimuth by remember { mutableStateOf(0f) }
    var qiblaAngle by remember { mutableStateOf(0f) }
    var directionToQibla by remember { mutableStateOf(0f) }
    var isFacingQibla by remember { mutableStateOf(false) }
    var locationGranted by remember { mutableStateOf(false) }

    // Request location permission
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted -> locationGranted = granted }

    LaunchedEffect(Unit) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) locationGranted = true
        else permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    // Sensor listener setup
    DisposableEffect(locationGranted) {
        if (!locationGranted) return@DisposableEffect onDispose {}

        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val rotationMatrix = FloatArray(9)
        val orientationValues = FloatArray(3)
        val gravity = FloatArray(3)
        val geomagnetic = FloatArray(3)

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                when (event.sensor.type) {
                    Sensor.TYPE_ACCELEROMETER -> System.arraycopy(event.values, 0, gravity, 0, 3)
                    Sensor.TYPE_MAGNETIC_FIELD -> System.arraycopy(event.values, 0, geomagnetic, 0, 3)
                }

                if (SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geomagnetic)) {
                    SensorManager.getOrientation(rotationMatrix, orientationValues)
                    var angle = Math.toDegrees(orientationValues[0].toDouble()).toFloat()
                    if (angle < 0) angle += 360
                    azimuth = angle
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        val accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val magnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        sensorManager.registerListener(listener, accel, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(listener, magnet, SensorManager.SENSOR_DELAY_UI)

        onDispose { sensorManager.unregisterListener(listener) }
    }

    // Calculate Qibla direction (Mecca coordinates)
    val qiblaLat = 21.4225
    val qiblaLon = 39.8262

    val userLocation = remember(locationGranted) {
        if (locationGranted) getLastKnownLocation(context) else null
    }

    userLocation?.let { location ->
        val userLat = Math.toRadians(location.latitude)
        val userLon = Math.toRadians(location.longitude)
        val kaabaLat = Math.toRadians(qiblaLat)
        val kaabaLon = Math.toRadians(qiblaLon)

        val lonDiff = kaabaLon - userLon
        val y = sin(lonDiff) * cos(kaabaLat)
        val x = cos(userLat) * sin(kaabaLat) - sin(userLat) * cos(kaabaLat) * cos(lonDiff)
        var bearing = Math.toDegrees(atan2(y, x))
        if (bearing < 0) bearing += 360
        qiblaAngle = bearing.toFloat()

        // Correct azimuth using geomagnetic declination
        val geoField = GeomagneticField(
            location.latitude.toFloat(),
            location.longitude.toFloat(),
            location.altitude.toFloat(),
            System.currentTimeMillis()
        )
        val declination = geoField.declination
        val correctedAzimuth = (azimuth + declination + 360) % 360

        // Compute direction to Qibla
        val direction = (qiblaAngle - correctedAzimuth + 360) % 360
        directionToQibla = direction
        isFacingQibla = direction in 0f..10f || direction in 350f..360f
    }

    Scaffold(
        topBar = { CommonActionBar(title = "Qibla Direction", navController = navController) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAF9))
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (!locationGranted) {
                Text(
                    text = "Please grant location permission to detect Qibla",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(32.dp)
                )
            } else {
                CompassUI(
                    rotation = azimuth,
                    needleRotation = directionToQibla,
                    isFacingQibla = isFacingQibla
                )
            }
        }
    }
}

// Fetch last known location
fun getLastKnownLocation(context: Context): Location? {
    return try {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = locationManager.getProviders(true)
        var bestLocation: Location? = null
        for (provider in providers) {
            val location = locationManager.getLastKnownLocation(provider) ?: continue
            if (bestLocation == null || location.accuracy < bestLocation.accuracy) {
                bestLocation = location
            }
        }
        bestLocation
    } catch (e: SecurityException) {
        null
    }
}
