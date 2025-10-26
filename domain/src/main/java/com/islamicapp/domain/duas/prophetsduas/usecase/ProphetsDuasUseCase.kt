package com.islamicapp.domain.duas.prophetsduas.usecase

import com.islamicapp.domain.duas.prophetsduas.repository.ProphetsDuasRepository

class ProphetsDuasUseCase(
    private val repository: ProphetsDuasRepository
) {
    suspend fun execute() = repository.getProphetsDuas()
}