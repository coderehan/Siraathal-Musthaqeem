package com.islamicapp.domain.allah99names.usecase

import com.islamicapp.domain.allah99names.model.Allah99Names
import com.islamicapp.domain.allah99names.repository.Allah99NamesRepository

class GetAllah99NamesUseCase(
    private val repository: Allah99NamesRepository
) {
    suspend operator fun invoke(): List<Allah99Names> {
        return repository.getAllah99Names()
    }
}