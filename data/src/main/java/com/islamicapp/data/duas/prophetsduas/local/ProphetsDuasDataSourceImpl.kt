package com.islamicapp.data.duas.prophetsduas.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.islamicapp.domain.duas.prophetsduas.model.ProphetsDuas

class ProphetsDuasDataSourceImpl(
    private val context: Context
) : ProphetsDuasDataSource {

    override suspend fun getProphetsDuas(): List<ProphetsDuas> {
        return try {
            val json = context.assets.open("prophets_duas/dua_of_various_prophets.json")
                .bufferedReader()
                .use { it.readText() }

            val type = object : TypeToken<List<ProphetsDuas>>() {}.type
            Gson().fromJson(json, type)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}