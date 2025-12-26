package com.islamicapp.domain.duas.masnoonduas.repository

import com.islamicapp.domain.duas.masnoonduas.model.MasnoonDuasTopic

interface MasnoonDuasRepository {
    /**
     * Fetches all Masnoon Duas topics from local assets
     */
    suspend fun getMasnoonDuasTopics(): List<MasnoonDuasTopic>?
}