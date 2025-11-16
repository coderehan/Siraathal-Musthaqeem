package com.islamicapp.data.seerah.repository

import com.islamicapp.data.seerah.source.local.SeerahDataSource
import com.islamicapp.domain.seerah.model.Seerah
import com.islamicapp.domain.seerah.repository.SeerahRepository

class SeerahRepositoryImpl(
    private val dataSource: SeerahDataSource
) : SeerahRepository {

    override suspend fun getSeerah(fileName: String): List<Seerah>? {
        return dataSource.getSeerahFromAssets(fileName)
    }
}