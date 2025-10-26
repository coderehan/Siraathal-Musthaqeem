package com.islamicapp.data.duas.quranicduas.local

import android.content.Context
import com.google.gson.Gson
import com.islamicapp.domain.duas.quranicduas.model.QuranicDuas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException

class QuranicDuasDataSourceImpl(
    private val context: Context
) : QuranicDuasDataSource {

    override suspend fun loadQuranicDuas(fileName: String): List<QuranicDuas> =
        withContext(Dispatchers.IO) {
            try {
                // Load JSON from subfolder under assets
                val json = context.assets.open("quranic_duas/$fileName")
                    .bufferedReader()
                    .use { it.readText() }

                val response = Gson().fromJson(json, QuranicDuasResponse::class.java)
                response.duas
            } catch (e: FileNotFoundException) {
                emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }

    data class QuranicDuasResponse(
        val duas: List<QuranicDuas>
    )
}
