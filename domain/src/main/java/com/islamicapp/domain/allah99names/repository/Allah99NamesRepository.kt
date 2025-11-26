package com.islamicapp.domain.allah99names.repository

import com.islamicapp.domain.allah99names.model.Allah99Names

/**
 * Repository interface for Allah 99 Names.
 *
 * The domain layer depends on this interface only,
 * not on the actual implementation. This allows for
 * easy swapping of data sources (e.g., API, cache).
 */

interface Allah99NamesRepository {
    suspend fun getAllah99Names(): List<Allah99Names>?
}