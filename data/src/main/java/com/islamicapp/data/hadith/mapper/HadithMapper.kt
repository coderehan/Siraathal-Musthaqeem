package com.islamicapp.data.hadith.mapper

import com.islamicapp.data.hadith.model.HadithResponse
import com.islamicapp.domain.hadith.model.Hadith
import com.islamicapp.domain.hadith.model.HadithItem

class HadithMapper {

    fun map(input: HadithResponse): List<Hadith> {
        val hadithName = input.metadata?.name.orEmpty()
        val sections = input.metadata?.sections ?: emptyMap()
        val sectionDetails = input.metadata?.section_details ?: emptyMap()

        // Filter out sections with empty titles
        return sections
            .filter { (id, title) -> !title.isNullOrBlank() }
            .map { (sectionId, sectionTitle) ->
                val sectionRange = sectionDetails[sectionId]
                val hadithFirst = sectionRange?.hadithnumber_first ?: 0.0
                val hadithLast = sectionRange?.hadithnumber_last ?: 0.0

                // Filter hadiths belonging to this section based on hadithnumber range
                val filteredHadiths = input.hadiths
                    ?.filter { item ->
                        val number = item.hadithnumber ?: 0.0
                        number in hadithFirst..hadithLast
                    }
                    ?.map { item ->
                        HadithItem(
                            hadithNumber = item.hadithnumber ?: 0.0,
                            hadithText = item.text.orEmpty(),
                            referenceBook = item.reference?.book ?: 0,
                            referenceHadith = item.reference?.hadith ?: 0
                        )
                    }
                    ?: emptyList()

                Hadith(
                    hadithName = hadithName,
                    sectionId = sectionId,
                    sectionTitle = sectionTitle,
                    hadithNumberFirst = hadithFirst,
                    hadithNumberLast = hadithLast,
                    hadiths = filteredHadiths
                )
            }
    }
}
