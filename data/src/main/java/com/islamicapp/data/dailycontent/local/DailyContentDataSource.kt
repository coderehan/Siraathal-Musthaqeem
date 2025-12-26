package com.islamicapp.data.dailycontent.local

import com.islamicapp.domain.dailycontent.model.DailyDua
import com.islamicapp.domain.dailycontent.model.DailyHadith

interface DailyContentDataSource {
    suspend fun getDailyHadithsFromAssets(fileName: String): List<DailyHadith>?
    suspend fun getDailyDuasFromAssets(fileName: String): List<DailyDua>?
}