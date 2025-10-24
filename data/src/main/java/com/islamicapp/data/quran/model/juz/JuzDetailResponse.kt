package com.islamicapp.data.quran.model.juz

data class JuzDetailResponse(
    val code: Int,
    val data: Data,
    val status: String
) {

    data class Data(
        val ayahs: List<Ayah>,
        val edition: Edition,
        val number: Int,
        val surahs: Surahs
    )

    data class Ayah(
        val hizbQuarter: Int,
        val juz: Int,
        val manzil: Int,
        val number: Int,
        val numberInSurah: Int,
        val page: Int,
        val ruku: Int,
        val sajda: Boolean,
        val surah: Surah,
        val text: String
    )

    data class Edition(
        val direction: String,
        val englishName: String,
        val format: String,
        val identifier: String,
        val language: String,
        val name: String,
        val type: String
    )

    data class Surah(
        val englishName: String,
        val englishNameTranslation: String,
        val name: String,
        val number: Int,
        val numberOfAyahs: Int,
        val revelationType: String
    )

    data class Surahs(
        val `1`: SurahInfo,
        val `2`: SurahInfo
    )

    data class SurahInfo(
        val englishName: String,
        val englishNameTranslation: String,
        val name: String,
        val number: Int,
        val numberOfAyahs: Int,
        val revelationType: String
    )

}