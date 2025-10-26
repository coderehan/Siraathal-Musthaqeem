package com.islamicapp.data.duas.rabbanaduas.repository

import com.islamicapp.data.duas.rabbanaduas.source.local.RabbanaDuasDataSource
import com.islamicapp.domain.duas.rabbanaduas.model.RabbanaDuas
import com.islamicapp.domain.duas.rabbanaduas.repository.RabbanaDuasRepository

class RabbanaDuasRepositoryImpl(
    private val dataSource: RabbanaDuasDataSource
) : RabbanaDuasRepository {

    override suspend fun getRabbanaDuas(): List<RabbanaDuas>? {
        return dataSource.getRabbanaDuasFromAssets()
    }
}