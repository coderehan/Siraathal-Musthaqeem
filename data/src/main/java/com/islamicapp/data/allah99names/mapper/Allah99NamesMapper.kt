package com.islamicapp.data.allah99names.mapper

import com.islamicapp.data.allah99names.model.Allah99NamesResponse
import com.islamicapp.domain.allah99names.model.Allah99Names

fun Allah99NamesResponse.toDomain(): List<Allah99Names> {
    return data.map {
        Allah99Names(
            englishMeaning = it.en.meaning,
            arabicName = it.name,
            number = it.number,
            englishTransliteration = it.transliteration
        )
    }
}

