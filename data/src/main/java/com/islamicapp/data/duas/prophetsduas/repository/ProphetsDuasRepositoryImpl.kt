package com.islamicapp.data.duas.prophetsduas.repository

import com.islamicapp.data.duas.prophetsduas.local.ProphetsDuasDataSource
import com.islamicapp.domain.duas.prophetsduas.model.ProphetsDuas
import com.islamicapp.domain.duas.prophetsduas.repository.ProphetsDuasRepository

class ProphetsDuasRepositoryImpl(
    private val dataSource: ProphetsDuasDataSource
) : ProphetsDuasRepository {

    override suspend fun getProphetsDuas(): List<ProphetsDuas> {
        return dataSource.getProphetsDuas()
    }
}