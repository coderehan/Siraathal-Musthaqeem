package com.islamicapp.data.hadith.model

data class HadithResponse(
    val hadiths: List<HadithUnifiedItem>? = null,
    val metadata: Metadata? = null
)

data class Metadata(
    val name: String? = null,
    val sections: Map<String, String>? = null,
    val section_details: Map<String, SectionRange>? = null
)

data class HadithUnifiedItem(
    val hadithnumber: Double? = null,
    val arabicnumber: Double? = null,
    val text: String? = null,
    val grades: List<GradeItem>? = null,
    val reference: ReferenceItem? = null
)

data class GradeItem(
    val name: String? = null,
    val grade: String? = null
)

data class ReferenceItem(
    val book: Int? = null,
    val hadith: Int? = null
)

data class SectionRange(
    val arabicnumber_first: Double? = null,
    val arabicnumber_last: Double? = null,
    val hadithnumber_first: Double? = null,
    val hadithnumber_last: Double? = null
)
