package com.islamicapp.domain.allah99names.model

/**
 * Domain model for representing one of the 99 Names of Allah.
 *
 * Note: This model is independent of API structure and
 * is tailored for use in the app’s business logic/UI.
 */

data class Allah99Names(
    val arabic: String,
    val transliteration: String,
    val translation: String,
    val number: Int = 0 // Default value allows direct deserialization from JSON
)

