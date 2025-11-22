package com.islamicapp.data.duas.masnoonduas.local

import android.app.Application
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.islamicapp.domain.duas.masnoonduas.model.MasnoonDuas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader

class MasnoonDuasDataSourceImpl(
    private val application: Application
) : MasnoonDuasDataSource {

    // Define all file paths to be loaded
    private val syllabusFiles = listOf(
        "masnoon_duas/basic_syllabus/grade1.json",
        "masnoon_duas/basic_syllabus/grade2.json",
        "masnoon_duas/basic_syllabus/grade3.json",
        "masnoon_duas/basic_syllabus/grade4.json",
        "masnoon_duas/basic_syllabus/grade5.json",
        "masnoon_duas/basic_syllabus/grade6.json",
        "masnoon_duas/basic_syllabus/grade7.json",
        "masnoon_duas/advance_syllabus/advance_duas.json"
    )

    private fun extractCategoryName(filePath: String): String {
        // Example: "masnoon_duas/basic_syllabus/grade1.json" -> "GRADE 1"
        // Example: "masnoon_duas/advance_syllabus/advance_duas.json" -> "ADVANCE SYLLABUS"

        val fileName = filePath.substringAfterLast('/').replace(".json", "")

        return when {
            fileName.startsWith("grade") -> "GRADE ${fileName.substringAfter("grade")}"
            fileName.startsWith("advance_duas") -> "ADVANCE SYLLABUS"
            else -> "MASNOON DUAS"
        }.uppercase()
    }

    override suspend fun getMasnoonDuasFromAssets(): List<MasnoonDuas>? {
        return withContext(Dispatchers.IO) {
            val allDuas = mutableListOf<MasnoonDuas>()
            val listType = object : TypeToken<List<MasnoonDuas>>() {}.type
            val gson = Gson()

            syllabusFiles.forEach { filePath ->
                try {
                    val categoryName = extractCategoryName(filePath)

                    application.assets.open(filePath).use { inputStream ->
                        val reader = InputStreamReader(inputStream)
                        val duasFromFile: List<MasnoonDuas>? = gson.fromJson(reader, listType)

                        if (duasFromFile != null) {
                            // Map over the list to inject the category name
                            val categorizedDuas = duasFromFile.map { dua ->
                                dua.copy(syllabusCategory = categoryName)
                            }
                            allDuas.addAll(categorizedDuas)
                        }
                    }
                } catch (e: Exception) {
                    println("Error loading Masnoon Dua file: $filePath")
                    e.printStackTrace()
                }
            }

            if (allDuas.isEmpty()) null else allDuas
        }
    }
}