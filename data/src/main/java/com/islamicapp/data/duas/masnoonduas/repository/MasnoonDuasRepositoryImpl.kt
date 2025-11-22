package com.islamicapp.data.duas.masnoonduas.repository

import com.islamicapp.data.duas.masnoonduas.local.MasnoonDuasDataSource
import com.islamicapp.domain.duas.masnoonduas.model.MasnoonDuas
import com.islamicapp.domain.duas.masnoonduas.repository.MasnoonDuasRepository

class MasnoonDuasRepositoryImpl(
    private val dataSource: MasnoonDuasDataSource
) : MasnoonDuasRepository {

    override suspend fun getMasnoonDuas(): List<MasnoonDuas>? {
        return dataSource.getMasnoonDuasFromAssets()
    }
}