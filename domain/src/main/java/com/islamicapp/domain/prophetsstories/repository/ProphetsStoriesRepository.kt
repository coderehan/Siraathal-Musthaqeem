package com.islamicapp.domain.prophetsstories.repository

import com.islamicapp.domain.prophetsstories.model.ProphetsStories

/**
 * Defines the contract for fetching prophet stories.
 *
 * Implemented by the data layer using local JSON source.
 */
interface ProphetsStoriesRepository {
    suspend fun getProphetsStories(fileName: String): ProphetsStories?
}
