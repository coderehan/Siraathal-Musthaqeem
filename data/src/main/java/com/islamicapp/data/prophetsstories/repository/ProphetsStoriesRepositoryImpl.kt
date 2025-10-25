package com.islamicapp.data.prophetsstories.repository

import com.islamicapp.data.prophetsstories.source.local.ProphetsStoriesDataSource
import com.islamicapp.domain.prophetsstories.model.ProphetsStories
import com.islamicapp.domain.prophetsstories.repository.ProphetsStoriesRepository

class ProphetsStoriesRepositoryImpl(
    private val dataSource: ProphetsStoriesDataSource
) : ProphetsStoriesRepository {

    override suspend fun getProphetsStories(fileName: String): ProphetsStories? {
        return dataSource.getProphetsStoriesFromAssets(fileName)
    }
}
