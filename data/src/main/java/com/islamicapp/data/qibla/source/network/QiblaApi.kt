package com.islamicapp.data.qibla.source.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface QiblaApi {

    @GET("v1/qibla/{lat}/{lng}/compass")
    @Streaming
    suspend fun getQiblaDirection(
        @Path("lat") latitude: Double,
        @Path("lng") longitude: Double
    ): ResponseBody

}

