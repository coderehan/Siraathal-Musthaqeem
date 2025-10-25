package com.islamicapp.data.prophetsstories.source.local

import android.content.Context
import com.google.gson.Gson
import com.islamicapp.domain.prophetsstories.model.ProphetsStories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Local implementation of ProphetsStoriesDataSource.
 * Reads story JSON files from assets/prophets/ folder.
 */
class ProphetsStoriesDataSourceImpl(
    private val context: Context
) : ProphetsStoriesDataSource {

    override suspend fun getProphetsStoriesFromAssets(fileName: String): ProphetsStories? {
        return withContext(Dispatchers.IO) {
            try {
                // JSON files are stored under assets/prophets/
                val inputStream = context.assets.open("prophets_stories/$fileName")
                val jsonString = inputStream.bufferedReader().use { it.readText() }

                Gson().fromJson(jsonString, ProphetsStories::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}