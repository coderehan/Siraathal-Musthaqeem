package com.islamicapp.domain.duas.masnoonduas.model

/**
 * Data class representing a single Masnoon Dua entry.
 */
data class MasnoonDuas(
    val title: String,
    val arabic: String,
    val translation: String,
    val virtue: String? = null,
    val reference: String? = null,
    // NEW FIELD to store the syllabus category (e.g., "GRADE 1")
    val syllabusCategory: String? = null
)