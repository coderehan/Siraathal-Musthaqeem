package com.islamicapp.domain.prophetsstories.model

data class ProphetsStories(
    val name: String,
    val story: List<Story>
) {
    data class Story(
        val introduction: String? = null,
        val points: List<Point>? = null,
        val surah: String? = null,
        val verses: List<Verse>? = null
    )

    data class Point(
        val point: Int? = null,
        val text: String? = null,
        val surah: String? = null,
        val verses: List<Verse>? = null
    )

    data class Verse(
        val verse: Int,
        val text: String
    )
}
