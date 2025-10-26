package com.islamicapp.domain.duas.quranicduas.repository

import com.islamicapp.domain.duas.quranicduas.model.QuranicDuas

interface QuranicDuasRepository {
    suspend fun getQuranicDuas(fileName: String): List<QuranicDuas>
}
