package com.islamicapp.domain.dailycontent.repository

import com.islamicapp.domain.dailycontent.model.DailyDua
import com.islamicapp.domain.dailycontent.model.DailyHadith

interface DailyContentRepository {
    suspend fun getAllHadiths(): List<DailyHadith>?
    suspend fun getAllDuas(): List<DailyDua>?
    suspend fun getDailyHadith(): DailyHadith?
    suspend fun getDailyDua(): DailyDua?
}