package com.islamicapp.data.duas.masnoonduas.repository

import com.islamicapp.data.duas.masnoonduas.local.MasnoonDuasDataSource
import com.islamicapp.domain.duas.masnoonduas.model.MasnoonDuasTopic
import com.islamicapp.domain.duas.masnoonduas.repository.MasnoonDuasRepository


class MasnoonDuasRepositoryImpl(
    private val dataSource: MasnoonDuasDataSource
) : MasnoonDuasRepository {

    override suspend fun getMasnoonDuasTopics(): List<MasnoonDuasTopic>? {
        return dataSource.getMasnoonDuasTopicsFromAssets()
    }
}