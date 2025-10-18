package com.islamicapp.domain.qibla.repository

import com.islamicapp.domain.qibla.model.Qibla

interface QiblaRepository {

    suspend fun getQiblaDirection(lat: Double, lng: Double): Qibla

}