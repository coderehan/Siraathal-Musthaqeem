package com.islamicapp.domain.seerah.repository

import com.islamicapp.domain.seerah.model.Seerah

interface SeerahRepository {
    suspend fun getSeerah(fileName: String): List<Seerah>?
}