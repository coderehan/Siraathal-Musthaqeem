package com.islamicapp.domain.hadith.repository

import com.islamicapp.core.utils.HadithCollectionType
import com.islamicapp.domain.hadith.model.Hadith

interface HadithRepository {
    suspend fun getHadiths(collection: HadithCollectionType): List<Hadith>
}