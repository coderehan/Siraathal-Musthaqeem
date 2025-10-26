package com.islamicapp.data.duas.emotionsduas.repository

import com.islamicapp.data.duas.emotionsduas.local.EmotionsDuasDataSource
import com.islamicapp.domain.duas.emotionsduas.model.EmotionsDuas
import com.islamicapp.domain.duas.emotionsduas.repository.EmotionsDuasRepository

class EmotionsDuasRepositoryImpl(
    private val dataSource: EmotionsDuasDataSource
) : EmotionsDuasRepository {

    override suspend fun getEmotionsDuas(fileName: String): List<EmotionsDuas>? {
        return dataSource.getEmotionsDuasFromAssets(fileName)
    }
}