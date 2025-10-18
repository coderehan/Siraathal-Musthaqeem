package com.islamicapp.data.hadith.source

import com.islamicapp.core.utils.HadithCollectionType
import com.islamicapp.domain.hadith.model.Hadith

interface HadithDataSource {

    suspend fun getHadiths(collection: HadithCollectionType): List<Hadith>

}