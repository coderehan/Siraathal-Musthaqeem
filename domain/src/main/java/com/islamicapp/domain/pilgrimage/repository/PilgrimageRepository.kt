package com.islamicapp.domain.pilgrimage.repository

import com.islamicapp.domain.pilgrimage.model.Pilgrimage

interface PilgrimageRepository {
    suspend fun getPilgrimage(fileName: String): Pilgrimage?
}