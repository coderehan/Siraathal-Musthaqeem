package com.islamicapp.domain.qibla.usecase

import com.islamicapp.domain.qibla.model.Qibla
import com.islamicapp.domain.qibla.repository.QiblaRepository

class QiblaUseCase(
    private val repository: QiblaRepository
) {
    suspend operator fun invoke(lat: Double, lng: Double): Qibla {
        return repository.getQiblaDirection(lat, lng)
    }
}