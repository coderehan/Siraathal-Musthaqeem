package com.islamicapp.domain.duas.emotionsduas.usecase

import com.islamicapp.domain.duas.emotionsduas.model.EmotionsDuas
import com.islamicapp.domain.duas.emotionsduas.repository.EmotionsDuasRepository

class EmotionsDuasUseCase(
    private val repository: EmotionsDuasRepository
) {
    suspend operator fun invoke(fileName: String): List<EmotionsDuas>? {
        return repository.getEmotionsDuas(fileName)
    }
}