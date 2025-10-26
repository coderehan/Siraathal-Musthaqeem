package com.islamicapp.data.duas.quranicduas.local

import com.islamicapp.domain.duas.quranicduas.model.QuranicDuas

interface QuranicDuasDataSource {
    suspend fun loadQuranicDuas(fileName: String): List<QuranicDuas>
}
