package com.islamicapp.domain.hadith.usecase

import com.islamicapp.core.utils.HadithCollectionType
import com.islamicapp.domain.hadith.model.Hadith
import com.islamicapp.domain.hadith.repository.HadithRepository

class HadithUseCase(
    private val repository: HadithRepository
) {
    suspend operator fun invoke(collection: HadithCollectionType): List<Hadith> {
        return repository.getHadiths(collection)
    }
}