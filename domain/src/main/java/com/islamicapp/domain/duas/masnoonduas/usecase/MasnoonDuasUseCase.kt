package com.islamicapp.domain.duas.masnoonduas.usecase

import com.islamicapp.domain.duas.masnoonduas.model.MasnoonDuas
import com.islamicapp.domain.duas.masnoonduas.repository.MasnoonDuasRepository

class MasnoonDuasUseCase(
    private val repository: MasnoonDuasRepository
) {
    /**
     * Executes the use case to retrieve Masnoon Duas.
     */
    suspend operator fun invoke(): List<MasnoonDuas>? {
        return repository.getMasnoonDuas()
    }
}