package com.islamicapp.siraathalmusthaqeem.screen.qibla

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamicapp.core.location.LocationProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.islamicapp.domain.qibla.usecase.QiblaUseCase

class QiblaViewModel(
    private val locationProvider: LocationProvider,
    private val getQiblaDirectionUseCase: QiblaUseCase,
    context: Context
) : ViewModel(), SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    private var userLat: Double = 0.0
    private var userLng: Double = 0.0

    private val _qiblaAngle = MutableStateFlow(0f)
    val qiblaAngle: StateFlow<Float> = _qiblaAngle

    private val _qiblaImage = MutableStateFlow<ByteArray?>(null)
    val qiblaImage: StateFlow<ByteArray?> = _qiblaImage

    init {
        viewModelScope.launch {
            try {
                val location = locationProvider.getCurrentLocation()
                userLat = location.latitude
                userLng = location.longitude
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        startSensors()
    }

    fun fetchQiblaImage() {
        viewModelScope.launch {
            try {
                val result = getQiblaDirectionUseCase(userLat, userLng)
                _qiblaImage.value = result.imageBytes
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun startSensors() {
        rotationVectorSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
            val rotationMatrix = FloatArray(9)
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
            val orientationAngles = FloatArray(3)
            SensorManager.getOrientation(rotationMatrix, orientationAngles)
            val azimuth = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
            val bearing = calculateQiblaBearing(userLat, userLng)
            _qiblaAngle.value = (bearing - azimuth + 360) % 360
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }

    fun calculateQiblaBearing(lat: Double, lng: Double): Float {
        val kaabaLat = Math.toRadians(21.4225)
        val kaabaLng = Math.toRadians(39.8262)

        val userLatRad = Math.toRadians(lat)
        val userLngRad = Math.toRadians(lng)

        val deltaLng = kaabaLng - userLngRad
        val x = Math.sin(deltaLng)
        val y = Math.cos(userLatRad) * Math.tan(kaabaLat) - Math.sin(userLatRad) * Math.cos(deltaLng)

        return Math.toDegrees(Math.atan2(x, y)).toFloat()
    }
}



