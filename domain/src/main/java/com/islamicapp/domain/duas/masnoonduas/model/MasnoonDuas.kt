package com.islamicapp.domain.duas.masnoonduas.model

/**
 * Data class representing a single Masnoon Dua entry.
 */
data class MasnoonDuas(
    val title: String,
    val arabic: String,
    val transliteration: String,
    val translation: String
)