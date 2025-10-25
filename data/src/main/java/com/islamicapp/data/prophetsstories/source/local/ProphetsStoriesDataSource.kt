package com.islamicapp.data.prophetsstories.source.local

import com.islamicapp.domain.prophetsstories.model.ProphetsStories

/**
 * DataSource interface for accessing Prophets stories data.
 *
 * - In remote source, this would make an API call.
 * - Here, it simply loads data from JSON files stored in assets.
 */
interface ProphetsStoriesDataSource {
    suspend fun getProphetsStoriesFromAssets(fileName: String): ProphetsStories?
}
