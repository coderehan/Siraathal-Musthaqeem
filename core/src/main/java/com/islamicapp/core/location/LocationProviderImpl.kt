package com.islamicapp.core.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import com.islamicapp.core.location.Location

class LocationProviderImpl(
    private val context: Context
) : LocationProvider {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission") // Make sure permissions are checked
    override suspend fun getCurrentLocation(): Location =
        suspendCancellableCoroutine { cont ->

            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).addOnSuccessListener { loc ->
                if (loc != null) {
                    cont.resume(
                        Location(
                            latitude = loc.latitude,
                            longitude = loc.longitude
                        )
                    )
                } else {
                    cont.resumeWithException(Exception("Location not available"))
                }
            }.addOnFailureListener { e ->
                cont.resumeWithException(e)
            }
        }
}
