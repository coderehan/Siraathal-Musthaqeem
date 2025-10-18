package com.islamicapp.core.location

import com.islamicapp.core.location.Location

interface LocationProvider {

    suspend fun getCurrentLocation(): Location

}