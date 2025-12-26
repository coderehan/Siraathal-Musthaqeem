package com.islamicapp.domain.duas.masnoonduas.usecase

import com.islamicapp.domain.duas.masnoonduas.model.MasnoonDuasTopic
import com.islamicapp.domain.duas.masnoonduas.repository.MasnoonDuasRepository

class MasnoonDuasUseCase(
    private val repository: MasnoonDuasRepository
) {
    /**
     * Executes the use case to retrieve all Masnoon Duas topics
     */
    suspend operator fun invoke(): List<MasnoonDuasTopic>? {
        return repository.getMasnoonDuasTopics()
    }
}