package com.islamicapp.data.qibla.source

import com.islamicapp.domain.qibla.model.Qibla

interface QiblaDataSource {

    suspend fun getQiblaDirection(lat: Double, lng: Double): Qibla

}
