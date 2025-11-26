package com.islamicapp.data.allah99names.source.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.islamicapp.domain.allah99names.model.Allah99Names
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Allah99NamesDataSourceImpl(
    private val context: Context
) : Allah99NamesDataSource {

    override suspend fun getAllah99NamesFromAssets(): List<Allah99Names>? {
        return withContext(Dispatchers.IO) {
            try {
                // JSON file path under assets/names_of_allah/names_of_allah.json
                val inputStream = context.assets.open("names_of_allah/names_of_allah.json")
                val jsonString = inputStream.bufferedReader().use { it.readText() }

                // Use the domain model for deserialization
                val listType = object : TypeToken<List<Allah99Names>>() {}.type
                Gson().fromJson<List<Allah99Names>>(jsonString, listType)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}