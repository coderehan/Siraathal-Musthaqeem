package com.islamicapp.siraathalmusthaqeem.allah99names.repository

import com.islamicapp.siraathalmusthaqeem.allah99names.model.Allah99Names

interface Allah99NamesRepository {

    suspend fun getAllah99Names(): List<Allah99Names>

}