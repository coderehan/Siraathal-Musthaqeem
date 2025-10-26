package com.islamicapp.data.duas.quranicduas.repository

import com.islamicapp.data.duas.quranicduas.local.QuranicDuasDataSource
import com.islamicapp.domain.duas.quranicduas.model.QuranicDuas
import com.islamicapp.domain.duas.quranicduas.repository.QuranicDuasRepository

class QuranicDuasRepositoryImpl(
    private val dataSource: QuranicDuasDataSource
) : QuranicDuasRepository {
    override suspend fun getQuranicDuas(fileName: String): List<QuranicDuas> {
        return dataSource.loadQuranicDuas(fileName)
    }
}