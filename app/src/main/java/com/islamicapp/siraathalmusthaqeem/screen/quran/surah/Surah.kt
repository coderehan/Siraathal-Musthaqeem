package com.islamicapp.siraathalmusthaqeem.screen.quran.surah

data class Surah(
    val surahNumber: Int,
    val surahNameEnglish: String,
    val surahNameArabic: String,
    val numberOfAyahs: Int,
    val revelationType: String,
    val englishNameTranslation: String = ""
)