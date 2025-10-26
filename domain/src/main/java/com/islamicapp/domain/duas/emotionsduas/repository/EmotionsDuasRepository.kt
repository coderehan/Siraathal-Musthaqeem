package com.islamicapp.domain.duas.emotionsduas.repository

import com.islamicapp.domain.duas.emotionsduas.model.EmotionsDuas


interface EmotionsDuasRepository {
    suspend fun getEmotionsDuas(fileName: String): List<EmotionsDuas>?
}