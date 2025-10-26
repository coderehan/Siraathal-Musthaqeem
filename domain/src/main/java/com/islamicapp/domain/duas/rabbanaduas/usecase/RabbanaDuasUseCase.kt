package com.islamicapp.domain.duas.rabbanaduas.usecase

import com.islamicapp.domain.duas.rabbanaduas.model.RabbanaDuas
import com.islamicapp.domain.duas.rabbanaduas.repository.RabbanaDuasRepository

class RabbanaDuasUseCase(
    private val repository: RabbanaDuasRepository
) {
    suspend operator fun invoke(): List<RabbanaDuas>? {
        return repository.getRabbanaDuas()
    }
}