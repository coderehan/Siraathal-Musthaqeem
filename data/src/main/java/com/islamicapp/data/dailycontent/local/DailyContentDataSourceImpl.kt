package com.islamicapp.data.dailycontent.local

import android.content.Context
import com.islamicapp.domain.dailycontent.model.DailyDua
import com.islamicapp.domain.dailycontent.model.DailyHadith
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DailyContentDataSourceImpl(
    private val context: Context
) : DailyContentDataSource {

    override suspend fun getDailyHadithsFromAssets(fileName: String): List<DailyHadith>? {
        return withContext(Dispatchers.IO) {
            try {
                // fileName already contains the full path from repository
                val inputStream = context.assets.open(fileName)
                val jsonString = inputStream.bufferedReader().use { it.readText() }

                val listType = object : TypeToken<List<DailyHadith>>() {}.type
                Gson().fromJson<List<DailyHadith>>(jsonString, listType)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    override suspend fun getDailyDuasFromAssets(fileName: String): List<DailyDua>? {
        return withContext(Dispatchers.IO) {
            try {
                // fileName already contains the full path from repository
                val inputStream = context.assets.open(fileName)
                val jsonString = inputStream.bufferedReader().use { it.readText() }

                val listType = object : TypeToken<List<DailyDua>>() {}.type
                Gson().fromJson<List<DailyDua>>(jsonString, listType)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}