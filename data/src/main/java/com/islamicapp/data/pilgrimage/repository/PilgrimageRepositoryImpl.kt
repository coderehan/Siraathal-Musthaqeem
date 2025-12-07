package com.islamicapp.data.pilgrimage.repository

import com.islamicapp.data.pilgrimage.source.local.PilgrimageDataSource
import com.islamicapp.domain.pilgrimage.model.Pilgrimage
import com.islamicapp.domain.pilgrimage.repository.PilgrimageRepository

class PilgrimageRepositoryImpl(
    private val dataSource: PilgrimageDataSource
) : PilgrimageRepository {

    override suspend fun getPilgrimage(fileName: String): Pilgrimage? {
        return dataSource.getPilgrimageFromAssets(fileName)
    }
}