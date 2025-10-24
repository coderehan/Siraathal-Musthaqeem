package com.islamicapp.data.quran.model.juz

import com.google.gson.annotations.JsonAdapter
import com.google.gson.JsonDeserializer
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class JuzDetailResponse(
    val code: Int,
    val data: Data,
    val status: String
) {

    data class Data(
        val ayahs: List<Ayah>,
        val edition: Edition,
        val number: Int,
        val surahs: Surahs
    )

    data class Ayah(
        val hizbQuarter: Int,
        val juz: Int,
        val manzil: Int,
        val number: Int,
        val numberInSurah: Int,
        val page: Int,
        val ruku: Int,
        @JsonAdapter(JuzSajdaDeserializer::class)
        val sajda: SajdaInfo?,
        val surah: Surah,
        val text: String
    )

    data class Edition(
        val direction: String,
        val englishName: String,
        val format: String,
        val identifier: String,
        val language: String,
        val name: String,
        val type: String
    )

    data class Surah(
        val englishName: String,
        val englishNameTranslation: String,
        val name: String,
        val number: Int,
        val numberOfAyahs: Int,
        val revelationType: String
    )

    data class Surahs(
        val `1`: SurahInfo?,
        val `2`: SurahInfo?
    )

    data class SurahInfo(
        val englishName: String,
        val englishNameTranslation: String,
        val name: String,
        val number: Int,
        val numberOfAyahs: Int,
        val revelationType: String
    )

    // Sajda info (object or boolean)
    data class SajdaInfo(
        val hasSajda: Boolean,
        val id: Int? = null,
        val recommended: Boolean? = null,
        val obligatory: Boolean? = null
    )
}

/**
 * Custom deserializer for Juz Sajda field
 * Handles both:
 *   - Boolean: "sajda": true / false
 *   - Object: "sajda": { "id": 15, "recommended": false, "obligatory": true }
 */
class JuzSajdaDeserializer : JsonDeserializer<JuzDetailResponse.SajdaInfo?> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): JuzDetailResponse.SajdaInfo? {
        if (json == null || json.isJsonNull) return null

        return try {
            when {
                // Case 1: sajda is a simple boolean
                json.isJsonPrimitive && json.asJsonPrimitive.isBoolean -> {
                    val hasSajda = json.asBoolean
                    JuzDetailResponse.SajdaInfo(hasSajda = hasSajda)
                }

                // Case 2: sajda is an object
                json.isJsonObject -> {
                    val jsonObj = json.asJsonObject
                    JuzDetailResponse.SajdaInfo(
                        hasSajda = true,
                        id = jsonObj.get("id")?.asInt,
                        recommended = jsonObj.get("recommended")?.asBoolean,
                        obligatory = jsonObj.get("obligatory")?.asBoolean
                    )
                }

                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }
}