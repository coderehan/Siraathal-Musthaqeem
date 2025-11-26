package com.islamicapp.domain.allah99names.usecase

import com.islamicapp.domain.allah99names.model.Allah99Names
import com.islamicapp.domain.allah99names.repository.Allah99NamesRepository

/**
 * Use case for fetching the list of Allah 99 Names.
 *
 * This sits in the domain layer and orchestrates data retrieval.
 * - It calls the repository (domain → data).
 * - The repository fetches from the correct data source (API/DB).
 */

class Allah99NamesUseCase(
    private val repository: Allah99NamesRepository
) {
    suspend operator fun invoke(): List<Allah99Names>? {
        return repository.getAllah99Names()?.mapIndexed { index, name ->
            // Map the simple model to the final model, calculating the 'number' (1-based index)
            name.copy(number = index + 1)
        }
    }
}