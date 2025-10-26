package com.islamicapp.domain.duas.prophetsduas.model

data class ProphetsDuas(
    val prophet_name: String,
    val duas: List<Dua>
) {
    data class Dua(
        val title: String,
        val arabic_dua: String,
        val transliteration: String,
        val translation: String
    )
}