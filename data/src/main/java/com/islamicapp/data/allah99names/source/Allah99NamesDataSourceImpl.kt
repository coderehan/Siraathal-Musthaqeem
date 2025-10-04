package com.islamicapp.data.allah99names.source

import com.islamicapp.domain.allah99names.model.Allah99Names
import com.islamicapp.domain.allah99names.repository.Allah99NamesRepository

/**
 * Repository implementation that adapts [Allah99NamesDataSource]
 * into [Allah99NamesRepository].
 *
 * This separates the domain layer (use cases) from the data layer.
 */

class Allah99NamesDataSourceImpl(
    private val dataSource: Allah99NamesDataSource
) : Allah99NamesRepository {

    override suspend fun getAllah99Names(): List<Allah99Names> {
        return dataSource.getAllah99Names()
    }
}