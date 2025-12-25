package com.islamicapp.siraathalmusthaqeem.utils

import android.content.Context
import android.location.Geocoder
import java.util.Locale

object LocationHelper {

    fun getCountryFromLocation(context: Context, latitude: Double, longitude: Double): String? {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            addresses?.firstOrNull()?.countryCode
        } catch (e: Exception) {
            null
        }
    }

    fun getLocationName(context: Context, latitude: Double, longitude: Double): String {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            val address = addresses?.firstOrNull()

            val parts = mutableListOf<String>()
            address?.subLocality?.let { parts.add(it) }
            address?.locality?.let { parts.add(it) }
            address?.adminArea?.let { parts.add(it) }
            address?.countryName?.let { parts.add(it) }

            parts.joinToString(", ")
        } catch (e: Exception) {
            "Unknown Location"
        }
    }
}