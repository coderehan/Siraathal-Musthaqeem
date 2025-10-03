package com.islamicapp.siraathalmusthaqeem.allah99names.source

import com.islamicapp.siraathalmusthaqeem.allah99names.model.Allah99Names

interface Allah99NamesDataSource {

    suspend fun getAllah99Names(): List<Allah99Names>

}