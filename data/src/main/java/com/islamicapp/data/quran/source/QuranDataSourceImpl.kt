package com.islamicapp.data.quran.source

import com.islamicapp.domain.quran.model.Surah
import com.islamicapp.domain.quran.repository.QuranRepository

class QuranDataSourceImpl(
    private val dataSource: QuranDataSource
) : QuranRepository {

    override suspend fun getSurahList(): List<Surah> {
        return dataSource.getSurahList()
    }

    override suspend fun getSurahDetail(surahNumber: Int): List<Surah> {
        return dataSource.getSurahDetail(surahNumber)
    }

    override suspend fun getJuzDetail(juzNumber: Int): List<Surah> {
        return dataSource.getJuzDetail(juzNumber)
    }
}