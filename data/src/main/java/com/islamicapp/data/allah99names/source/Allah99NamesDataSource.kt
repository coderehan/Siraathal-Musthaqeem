package com.islamicapp.data.allah99names.source

import com.islamicapp.domain.allah99names.model.Allah99Names

interface Allah99NamesDataSource {

    suspend fun getAllah99Names(): List<Allah99Names>

}