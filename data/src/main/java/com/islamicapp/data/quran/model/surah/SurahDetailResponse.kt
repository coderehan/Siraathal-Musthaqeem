package com.islamicapp.data.quran.model.surah

import com.google.gson.annotations.JsonAdapter
import com.google.gson.JsonDeserializer
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class SurahDetailResponse(
    val code: Int,
    val data: Data,
    val status: String
) {

    data class Data(
        val ayahs: List<Ayah>,
        val edition: Edition,
        val englishName: String,
        val englishNameTranslation: String,
        val name: String,
        val number: Int,
        val numberOfAyahs: Int,
        val revelationType: String
    )

    data class Ayah(
        val hizbQuarter: Int,
        val juz: Int,
        val manzil: Int,
        val number: Int,
        val numberInSurah: Int,
        val page: Int,
        val ruku: Int,
        @JsonAdapter(SajdaDeserializer::class)
        val sajda: SajdaInfo?,
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

    // Sajda can be either boolean or object
    data class SajdaInfo(
        val hasSajda: Boolean,
        val id: Int? = null,
        val recommended: Boolean? = null,
        val obligatory: Boolean? = null
    )
}

// Custom deserializer to handle both boolean and object types
class SajdaDeserializer : JsonDeserializer<SurahDetailResponse.SajdaInfo?> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): SurahDetailResponse.SajdaInfo? {
        if (json == null || json.isJsonNull) {
            return null
        }

        return try {
            when {
                // Case 1: sajda is a boolean (true/false)
                json.isJsonPrimitive && json.asJsonPrimitive.isBoolean -> {
                    val hasSajda = json.asBoolean
                    SurahDetailResponse.SajdaInfo(hasSajda = hasSajda)
                }

                // Case 2: sajda is an object with id, recommended, obligatory
                json.isJsonObject -> {
                    val jsonObject = json.asJsonObject
                    SurahDetailResponse.SajdaInfo(
                        hasSajda = true,  // If object exists, it means there's a sajda
                        id = jsonObject.get("id")?.asInt,
                        recommended = jsonObject.get("recommended")?.asBoolean,
                        obligatory = jsonObject.get("obligatory")?.asBoolean
                    )
                }

                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }
}