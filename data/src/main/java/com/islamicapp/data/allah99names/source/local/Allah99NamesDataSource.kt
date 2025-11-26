package com.islamicapp.data.allah99names.source.local

import com.islamicapp.domain.allah99names.model.Allah99Names

interface Allah99NamesDataSource {
    suspend fun getAllah99NamesFromAssets(): List<Allah99Names>?
}