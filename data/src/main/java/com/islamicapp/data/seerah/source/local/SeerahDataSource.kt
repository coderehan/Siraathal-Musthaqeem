package com.islamicapp.data.seerah.source.local

import com.islamicapp.domain.seerah.model.Seerah

interface SeerahDataSource {
    suspend fun getSeerahFromAssets(fileName: String): List<Seerah>?
}