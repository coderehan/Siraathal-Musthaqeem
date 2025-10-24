package com.islamicapp.domain.quran.model

data class Surah(
    val surahNumber: Int,
    val surahNameArabic: String,
    val surahNameEnglish: String,
    val englishNameTranslation: String,
    val revelationType: String,
    val numberOfAyahs: Int,
    val ayahText: String,
    val numberInSurah: Int,
    val hasSajda: Boolean = false,
    val sajdaRecommended: Boolean? = null,
    val sajdaObligatory: Boolean? = null
)