package com.islamicapp.data.duas.emotionsduas.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.islamicapp.domain.duas.emotionsduas.model.EmotionsDuas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmotionsDuasDataSourceImpl(
    private val context: Context
) : EmotionsDuasDataSource {

    override suspend fun getEmotionsDuasFromAssets(fileName: String): List<EmotionsDuas>? {
        return withContext(Dispatchers.IO) {
            try {
                // JSON files are stored under assets/duas/emotions_duas/
                val inputStream = context.assets.open("emotions_duas/$fileName")
                val jsonString = inputStream.bufferedReader().use { it.readText() }

                val listType = object : TypeToken<List<EmotionsDuas>>() {}.type
                Gson().fromJson<List<EmotionsDuas>>(jsonString, listType)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}