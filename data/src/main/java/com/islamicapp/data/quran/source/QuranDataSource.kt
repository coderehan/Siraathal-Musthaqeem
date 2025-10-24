package com.islamicapp.data.quran.source

import com.islamicapp.domain.quran.model.Surah

interface QuranDataSource {

    suspend fun getSurahList(): List<Surah>

    suspend fun getSurahDetail(surahNumber: Int): List<Surah>

    suspend fun getJuzDetail(juzNumber: Int): List<Surah>
}