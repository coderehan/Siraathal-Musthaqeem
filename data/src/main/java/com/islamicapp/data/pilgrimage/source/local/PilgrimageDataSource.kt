package com.islamicapp.data.pilgrimage.source.local

import com.islamicapp.domain.pilgrimage.model.Pilgrimage

interface PilgrimageDataSource {
    suspend fun getPilgrimageFromAssets(fileName: String): Pilgrimage?
}