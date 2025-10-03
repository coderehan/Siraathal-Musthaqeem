package com.islamicapp.data.allah99names.source.network

import com.islamicapp.data.allah99names.mapper.toDomain
import com.islamicapp.data.allah99names.source.Allah99NamesDataSource
import com.islamicapp.domain.allah99names.model.Allah99Names

class Allah99NamesNetworkDataSource(private val api: Allah99NamesApi) : Allah99NamesDataSource {

    override suspend fun getAllah99Names(): List<Allah99Names> {
        return api.getAllah99Names().toDomain()
    }

}