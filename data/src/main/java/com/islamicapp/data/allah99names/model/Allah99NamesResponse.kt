package com.islamicapp.data.allah99names.model

// API response model
data class Allah99NamesResponse(
    val code: Int,
    val data: List<AllahName>,
    val status: String
) {
    data class AllahName(
        val en: MeaningEn,
        val name: String,
        val number: Int,
        val transliteration: String
    )
    data class MeaningEn(
        val meaning: String
    )
}
