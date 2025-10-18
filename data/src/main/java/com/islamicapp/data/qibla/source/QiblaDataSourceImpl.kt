package com.islamicapp.data.qibla.source

import com.islamicapp.domain.qibla.model.Qibla
import com.islamicapp.domain.qibla.repository.QiblaRepository

class QiblaDataSourceImpl(
    private val dataSource: QiblaDataSource
) : QiblaRepository {

    override suspend fun getQiblaDirection(lat: Double, lng: Double): Qibla {
        return dataSource.getQiblaDirection(lat, lng)
    }

}
