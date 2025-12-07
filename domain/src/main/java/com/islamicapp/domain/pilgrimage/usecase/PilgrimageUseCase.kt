package com.islamicapp.domain.pilgrimage.usecase

import com.islamicapp.domain.pilgrimage.model.Pilgrimage
import com.islamicapp.domain.pilgrimage.repository.PilgrimageRepository

class PilgrimageUseCase(
    private val repository: PilgrimageRepository
) {
    suspend operator fun invoke(fileName: String): Pilgrimage? {
        return repository.getPilgrimage(fileName)
    }
}