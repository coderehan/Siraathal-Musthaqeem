package com.islamicapp.domain.dailycontent.usecase

import com.islamicapp.domain.dailycontent.model.DailyHadith
import com.islamicapp.domain.dailycontent.repository.DailyContentRepository

class DailyHadithUseCase(
    private val repository: DailyContentRepository
) {
    suspend operator fun invoke(): DailyHadith? {
        return repository.getDailyHadith()
    }
}