package com.islamicapp.data.allah99names.source.network

import com.islamicapp.data.allah99names.model.Allah99NamesResponse
import retrofit2.http.GET

/**
 * Retrofit interface for fetching Allah 99 Names
 * from the backend service.
 */

interface Allah99NamesApi {

    @GET("/v1/asmaAlHusna")
    suspend fun getAllah99Names(): Allah99NamesResponse

}