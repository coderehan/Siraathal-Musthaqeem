package com.islamicapp.data.seerah.source.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.islamicapp.domain.seerah.model.Seerah
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

class SeerahDataSourceImpl(
    private val context: Context
) : SeerahDataSource {

    override suspend fun getSeerahFromAssets(fileName: String): List<Seerah>? {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream: InputStream = context.assets.open("seerah/$fileName")
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                val listType = object : TypeToken<List<Seerah>>() {}.type
                Gson().fromJson(jsonString, listType)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}