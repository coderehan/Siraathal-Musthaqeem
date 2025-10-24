package com.islamicapp.domain.quran.usecase

import com.islamicapp.domain.quran.model.Juz
import com.islamicapp.domain.quran.model.Surah
import com.islamicapp.domain.quran.repository.QuranRepository

class QuranUseCase(
    private val repository: QuranRepository
) {

    suspend fun getSurahList(): List<Surah> {
        return repository.getSurahList()
    }

    suspend fun getSurahDetail(surahNumber: Int): List<Surah> {
        return repository.getSurahDetail(surahNumber)
    }

    suspend fun getJuzDetail(juzNumber: Int): List<Juz> {
        return repository.getJuzDetail(juzNumber)
    }
}
