package com.islamicapp.data.allah99names.mapper

import com.islamicapp.data.allah99names.model.Allah99NamesResponse
import com.islamicapp.data.common.mapper.BaseMapper
import com.islamicapp.domain.allah99names.model.Allah99Names

/**
 * Mapper that converts API response models [Allah99NamesResponse]
 * into domain models [Allah99Names].
 *
 * Why? → Keeps API layer (data) separate from domain layer,
 * ensuring the domain layer is independent of API structure.
 */

class Allah99NamesMapper : BaseMapper<Allah99NamesResponse, List<Allah99Names>>() {
    /**
     * Maps [Allah99NamesResponse] (DTO) into a list of domain [Allah99Names].
     */
    override fun map(input: Allah99NamesResponse): List<Allah99Names> {
        return input.data.map {
            Allah99Names(
                englishMeaning = it.en.meaning,
                arabicName = it.name,
                number = it.number,
                englishTransliteration = it.transliteration
            )
        }
    }
}


