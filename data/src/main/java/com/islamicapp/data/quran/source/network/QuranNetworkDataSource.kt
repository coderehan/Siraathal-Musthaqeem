package com.islamicapp.data.quran.source.network

import com.islamicapp.data.quran.mapper.QuranMapper
import com.islamicapp.data.quran.source.QuranDataSource
import com.islamicapp.domain.quran.model.Surah

class QuranNetworkDataSource(
    private val api: QuranApi,
    private val mapper: QuranMapper
) : QuranDataSource {

    override suspend fun getSurahList(): List<Surah> {
        return api.getSurahList().run(mapper::mapSurahList)
    }

    override suspend fun getSurahDetail(surahNumber: Int): List<Surah> {
        return api.getSurahDetail(surahNumber).run(mapper::mapSurahDetail)
    }

    override suspend fun getJuzDetail(juzNumber: Int): List<Surah> {
        TODO("Not yet implemented")
    }

}