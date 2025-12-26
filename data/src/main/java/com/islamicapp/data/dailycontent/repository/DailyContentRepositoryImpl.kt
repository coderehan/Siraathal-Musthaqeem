package com.islamicapp.data.dailycontent.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.islamicapp.data.dailycontent.local.DailyContentDataSource
import com.islamicapp.domain.dailycontent.model.DailyDua
import com.islamicapp.domain.dailycontent.model.DailyHadith
import com.islamicapp.domain.dailycontent.repository.DailyContentRepository
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class DailyContentRepositoryImpl(
    private val dataSource: DailyContentDataSource
) : DailyContentRepository {

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        private val BASE_DATE = LocalDate.of(2025, 1, 1)
        private const val HADITH_FILE = "daily_content/daily_hadith.json"
        private const val DUA_FILE = "daily_content/daily_dua.json"
    }

    private var cachedHadiths: List<DailyHadith>? = null
    private var cachedDuas: List<DailyDua>? = null

    override suspend fun getAllHadiths(): List<DailyHadith>? {
        if (cachedHadiths == null) {
            cachedHadiths = dataSource.getDailyHadithsFromAssets(HADITH_FILE)
        }
        return cachedHadiths
    }

    override suspend fun getAllDuas(): List<DailyDua>? {
        if (cachedDuas == null) {
            cachedDuas = dataSource.getDailyDuasFromAssets(DUA_FILE)
        }
        return cachedDuas
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getDailyHadith(): DailyHadith? {
        val hadiths = getAllHadiths() ?: return null
        if (hadiths.isEmpty()) return null

        val dailyIndex = calculateDailyIndex(hadiths.size)
        return hadiths.getOrNull(dailyIndex)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getDailyDua(): DailyDua? {
        val duas = getAllDuas() ?: return null
        if (duas.isEmpty()) return null

        val dailyIndex = calculateDailyIndex(duas.size)
        return duas.getOrNull(dailyIndex)
    }

    /**
     * Calculate daily rotating index
     * Day 1 → Index 0, Day 2 → Index 1, ..., Day 101 → Index 0 (repeats)
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateDailyIndex(totalItems: Int): Int {
        val today = LocalDate.now()
        val daysSinceBase = ChronoUnit.DAYS.between(BASE_DATE, today)

        return (daysSinceBase % totalItems).toInt().let { index ->
            if (index < 0) index + totalItems else index
        }
    }
}