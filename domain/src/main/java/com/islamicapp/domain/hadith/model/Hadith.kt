package com.islamicapp.domain.hadith.model

data class Hadith(
    val hadithName: String,
    val sectionId: String,
    val sectionTitle: String,
    val hadithNumberFirst: Double,
    val hadithNumberLast: Double,
    val hadiths: List<HadithItem>
)

data class HadithItem(
    val hadithNumber: Double,
    val hadithText: String,
    val referenceBook: Int,
    val referenceHadith: Int
)