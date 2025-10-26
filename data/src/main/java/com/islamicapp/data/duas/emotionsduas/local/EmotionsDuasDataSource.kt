package com.islamicapp.data.duas.emotionsduas.local

import com.islamicapp.domain.duas.emotionsduas.model.EmotionsDuas

interface EmotionsDuasDataSource {
    suspend fun getEmotionsDuasFromAssets(fileName: String): List<EmotionsDuas>?
}