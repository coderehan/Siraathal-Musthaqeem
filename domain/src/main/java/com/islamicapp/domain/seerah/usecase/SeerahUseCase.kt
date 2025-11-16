package com.islamicapp.domain.seerah.usecase

import com.islamicapp.domain.seerah.model.Seerah
import com.islamicapp.domain.seerah.repository.SeerahRepository

class SeerahUseCase(
    private val repository: SeerahRepository
) {
    suspend operator fun invoke(fileName: String): List<Seerah>? {
        return repository.getSeerah(fileName)
    }
}