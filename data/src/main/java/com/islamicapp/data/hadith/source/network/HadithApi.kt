package com.islamicapp.data.hadith.source.network

import com.islamicapp.data.hadith.model.HadithResponse
import retrofit2.http.GET

interface HadithApi {

    @GET("eng-bukhari.json")
    suspend fun getSahihAlBukhariHadith(): HadithResponse

    @GET("eng-muslim.json")
    suspend fun getSahihMuslimHadith(): HadithResponse

    @GET("eng-nasai.json")
    suspend fun getSunanAnNasaiHadith(): HadithResponse

    @GET("eng-abudawud.json")
    suspend fun getSunanAbuDawudHadith(): HadithResponse

    @GET("eng-tirmidhi.json")
    suspend fun getJamiatTirmidhiHadith(): HadithResponse

}