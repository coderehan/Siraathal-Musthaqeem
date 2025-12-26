package com.islamicapp.domain.dailycontent.usecase

import com.islamicapp.domain.dailycontent.model.DailyDua
import com.islamicapp.domain.dailycontent.repository.DailyContentRepository

class DailyDuaUseCase(
    private val repository: DailyContentRepository
) {
    suspend operator fun invoke(): DailyDua? {
        return repository.getDailyDua()
    }
}