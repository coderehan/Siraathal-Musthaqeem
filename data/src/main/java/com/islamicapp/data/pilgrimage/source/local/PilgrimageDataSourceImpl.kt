package com.islamicapp.data.pilgrimage.source.local

import android.content.Context
import com.google.gson.Gson
import com.islamicapp.domain.pilgrimage.model.Pilgrimage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

class PilgrimageDataSourceImpl(
    private val context: Context
) : PilgrimageDataSource {

    override suspend fun getPilgrimageFromAssets(fileName: String): Pilgrimage? {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream: InputStream = context.assets.open("pilgrimage/$fileName")
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                Gson().fromJson(jsonString, Pilgrimage::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}