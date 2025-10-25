package com.islamicapp.domain.prophetsstories.usecase

import com.islamicapp.domain.prophetsstories.model.ProphetsStories
import com.islamicapp.domain.prophetsstories.repository.ProphetsStoriesRepository

/**
 * Use case for fetching a prophet's story.
 *
 * Invoked by ViewModel to load data from repository.
 */
class ProphetsStoriesUseCase(
    private val repository: ProphetsStoriesRepository
) {
    suspend operator fun invoke(fileName: String): ProphetsStories? {
        return repository.getProphetsStories(fileName)
    }
}