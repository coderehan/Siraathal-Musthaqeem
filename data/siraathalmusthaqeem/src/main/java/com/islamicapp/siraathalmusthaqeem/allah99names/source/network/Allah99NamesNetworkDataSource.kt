package com.islamicapp.siraathalmusthaqeem.allah99names.source.network

import com.islamicapp.siraathalmusthaqeem.allah99names.mapper.toDomain
import com.islamicapp.siraathalmusthaqeem.allah99names.model.Allah99Names
import com.islamicapp.siraathalmusthaqeem.allah99names.source.Allah99NamesDataSource

class Allah99NamesNetworkDataSource(private val api: Allah99NamesApi) : Allah99NamesDataSource {

    override suspend fun getAllah99Names(): List<Allah99Names> {
        return api.getAllah99Names().toDomain()
    }

}