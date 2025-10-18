package com.islamicapp.data.qibla.source.network

import com.islamicapp.data.qibla.mapper.QiblaMapper
import com.islamicapp.data.qibla.source.QiblaDataSource
import com.islamicapp.domain.qibla.model.Qibla

class QiblaNetworkDataSource(
    private val api: QiblaApi
) : QiblaDataSource {

    override suspend fun getQiblaDirection(lat: Double, lng: Double): Qibla {
        return api.getQiblaDirection(lat, lng).run(QiblaMapper()::map)
    }

}

