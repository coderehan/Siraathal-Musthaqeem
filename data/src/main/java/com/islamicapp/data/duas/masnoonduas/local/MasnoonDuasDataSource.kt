package com.islamicapp.data.duas.masnoonduas.local

import com.islamicapp.domain.duas.masnoonduas.model.MasnoonDuas

interface MasnoonDuasDataSource {
    /**
     * Retrieves the list of Masnoon Duas from the local assets.
     */
    suspend fun getMasnoonDuasFromAssets(): List<MasnoonDuas>?
}