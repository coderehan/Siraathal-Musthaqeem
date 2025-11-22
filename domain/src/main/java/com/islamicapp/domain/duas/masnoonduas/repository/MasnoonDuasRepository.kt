package com.islamicapp.domain.duas.masnoonduas.repository

import com.islamicapp.domain.duas.masnoonduas.model.MasnoonDuas

interface MasnoonDuasRepository {
    /**
     * Fetches the complete list of Masnoon Duas.
     */
    suspend fun getMasnoonDuas(): List<MasnoonDuas>?
}