package com.islamicapp.data.allah99names.source.network

import com.islamicapp.data.allah99names.mapper.Allah99NamesMapper
import com.islamicapp.data.allah99names.source.Allah99NamesDataSource
import com.islamicapp.domain.allah99names.model.Allah99Names

/**
 * Implementation of [Allah99NamesDataSource] that fetches data
 * from the network using [Allah99NamesApi].
 *
 * It uses [Allah99NamesMapper] to convert API models into
 * domain models before returning them.
 */

class Allah99NamesNetworkDataSource(
    private val api: Allah99NamesApi
) : Allah99NamesDataSource {

    override suspend fun getAllah99Names(): List<Allah99Names> {
        return api.getAllah99Names().run(Allah99NamesMapper()::map)     // mapping API model -> domain model
    }
}
