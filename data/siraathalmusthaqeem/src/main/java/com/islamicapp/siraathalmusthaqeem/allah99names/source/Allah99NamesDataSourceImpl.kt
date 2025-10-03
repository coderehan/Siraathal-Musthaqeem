package com.islamicapp.siraathalmusthaqeem.allah99names.source

import com.islamicapp.siraathalmusthaqeem.allah99names.model.Allah99Names
import com.islamicapp.siraathalmusthaqeem.allah99names.repository.Allah99NamesRepository

class Allah99NamesDataSourceImpl(
    private val dataSource: Allah99NamesDataSource
) : Allah99NamesRepository {

    override suspend fun getAllah99Names(): List<Allah99Names> {
        return dataSource.getAllah99Names()
    }
}