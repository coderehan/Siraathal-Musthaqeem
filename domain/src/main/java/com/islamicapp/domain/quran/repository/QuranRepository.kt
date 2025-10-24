package com.islamicapp.domain.quran.repository

import com.islamicapp.domain.quran.model.Juz
import com.islamicapp.domain.quran.model.Surah

interface QuranRepository {

    suspend fun getSurahList(): List<Surah>

    suspend fun getSurahDetail(surahNumber: Int): List<Surah>

    suspend fun getJuzDetail(juzNumber: Int): List<Juz>
}