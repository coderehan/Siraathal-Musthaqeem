package com.islamicapp.domain.dailycontent.model

data class DailyDua(
    val id: Int,
    val arabic: String,
    val transliteration: String,
    val translation: String
)