package com.islamicapp.data.quran.source.network

import com.islamicapp.data.quran.model.juz.JuzDetailResponse
import com.islamicapp.data.quran.model.surah.SurahDetailResponse
import com.islamicapp.data.quran.model.surah.SurahListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranApi {

    // Fetch list of all Surahs
    @GET("v1/surah")
    suspend fun getSurahList(): SurahListResponse

    // Fetch specific Surah detail by number (1–114)
    @GET("v1/surah/{number}")
    suspend fun getSurahDetail(
        @Path("number") surahNumber: Int
    ): SurahDetailResponse

    // Fetch specific Juz detail by number (1–30)
    @GET("v1/juz/{number}")
    suspend fun getJuzDetail(
        @Path("number") juzNumber: Int
    ): JuzDetailResponse

}
