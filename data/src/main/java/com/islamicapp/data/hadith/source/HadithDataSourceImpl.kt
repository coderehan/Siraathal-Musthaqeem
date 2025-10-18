package com.islamicapp.data.hadith.source

import com.islamicapp.core.utils.HadithCollectionType
import com.islamicapp.domain.hadith.model.Hadith
import com.islamicapp.domain.hadith.repository.HadithRepository

class HadithDataSourceImpl(
    private val dataSource: HadithDataSource
) : HadithRepository {

    override suspend fun getHadiths(collection: HadithCollectionType): List<Hadith> {
        return dataSource.getHadiths(collection)
    }
}