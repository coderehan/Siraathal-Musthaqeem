package com.islamicapp.siraathalmusthaqeem.allah99names.mapper

import com.islamicapp.siraathalmusthaqeem.allah99names.model.Allah99Names
import com.islamicapp.siraathalmusthaqeem.allah99names.model.Allah99NamesResponse

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

