package com.islamicapp.data.quran.model.surah

data class SurahListResponse(
    val code: Int,
    val data: List<Data>,
    val status: String
) {
    data class Data(
        val englishName: String,
        val englishNameTranslation: String,
        val name: String,
        val number: Int,
        val numberOfAyahs: Int,
        val revelationType: String
    )
}