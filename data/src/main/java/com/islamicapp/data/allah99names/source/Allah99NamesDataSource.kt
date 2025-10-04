package com.islamicapp.data.allah99names.source

import com.islamicapp.domain.allah99names.model.Allah99Names

/**
 * Defines the contract for data sources that can fetch
 * Allah 99 Names.
 *
 * Different implementations:
 * - [Allah99NamesNetworkDataSource] → fetch from API
 * - (Future) LocalDataSource → fetch from DB/cache
 */

interface Allah99NamesDataSource {

    suspend fun getAllah99Names(): List<Allah99Names>

}