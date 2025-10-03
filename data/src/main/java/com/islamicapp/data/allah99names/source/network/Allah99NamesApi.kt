package com.islamicapp.data.allah99names.source.network

import com.islamicapp.data.allah99names.model.Allah99NamesResponse
import retrofit2.http.GET

interface Allah99NamesApi {

    @GET("/v1/asmaAlHusna")
    suspend fun getAllah99Names(): Allah99NamesResponse

}