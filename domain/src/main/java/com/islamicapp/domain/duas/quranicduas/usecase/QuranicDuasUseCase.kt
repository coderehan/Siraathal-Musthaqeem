package com.islamicapp.domain.duas.quranicduas.usecase

import com.islamicapp.domain.duas.quranicduas.model.QuranicDuas
import com.islamicapp.domain.duas.quranicduas.repository.QuranicDuasRepository

class QuranicDuasUseCase(
    private val repository: QuranicDuasRepository
) {
    suspend fun execute(fileName: String): List<QuranicDuas> {
        return repository.getQuranicDuas(fileName)
    }
}
