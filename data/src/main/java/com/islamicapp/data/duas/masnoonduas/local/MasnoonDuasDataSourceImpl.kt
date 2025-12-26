package com.islamicapp.data.duas.masnoonduas.local

import android.app.Application
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.islamicapp.domain.duas.masnoonduas.model.MasnoonDuasTopic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

class MasnoonDuasDataSourceImpl(
    private val application: Application
) : MasnoonDuasDataSource {

    // Single JSON file path
    private val masnoonDuasFilePath = "masnoon_duas/masnoon_duas.json"

    override suspend fun getMasnoonDuasTopicsFromAssets(): List<MasnoonDuasTopic>? {
        return withContext(Dispatchers.IO) {
            try {
                application.assets.open(masnoonDuasFilePath).use { inputStream ->
                    val reader = InputStreamReader(inputStream)
                    val gson = Gson()

                    // Parse the JSON array of topics
                    val listType = object : TypeToken<List<MasnoonDuasTopic>>() {}.type
                    val topics: List<MasnoonDuasTopic>? = gson.fromJson(reader, listType)

                    topics
                }
            } catch (e: Exception) {
                println("Error loading Masnoon Duas: ${e.message}")
                e.printStackTrace()
                null
            }
        }
    }
}