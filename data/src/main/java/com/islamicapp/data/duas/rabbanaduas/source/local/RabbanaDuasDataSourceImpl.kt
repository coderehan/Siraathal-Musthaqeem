package com.islamicapp.data.duas.rabbanaduas.source.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.islamicapp.domain.duas.rabbanaduas.model.RabbanaDuas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RabbanaDuasDataSourceImpl(
    private val context: Context
) : RabbanaDuasDataSource {

    override suspend fun getRabbanaDuasFromAssets(): List<RabbanaDuas>? {
        return withContext(Dispatchers.IO) {
            try {
                // JSON file path under assets/duas/
                val inputStream = context.assets.open("rabbana_duas/40_rabbana_duas.json")
                val jsonString = inputStream.bufferedReader().use { it.readText() }

                val listType = object : TypeToken<List<RabbanaDuas>>() {}.type
                Gson().fromJson<List<RabbanaDuas>>(jsonString, listType)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}