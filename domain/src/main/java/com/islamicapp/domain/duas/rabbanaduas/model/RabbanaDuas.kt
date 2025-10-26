package com.islamicapp.domain.duas.rabbanaduas.model

data class RabbanaDuas(
    val header: String? = null,
    val arabicText: String,
    val transliteration: String,
    val translation: String,
    val recommendedUse: String
)