package com.islamicapp.data.duas.masnoonduas.local

import com.islamicapp.domain.duas.masnoonduas.model.MasnoonDuasTopic


interface MasnoonDuasDataSource {
    /**
     * Retrieves Masnoon Duas topics from local JSON file
     */
    suspend fun getMasnoonDuasTopicsFromAssets(): List<MasnoonDuasTopic>?
}