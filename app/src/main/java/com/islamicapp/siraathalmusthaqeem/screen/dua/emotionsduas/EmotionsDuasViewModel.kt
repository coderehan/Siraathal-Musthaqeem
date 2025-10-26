package com.islamicapp.siraathalmusthaqeem.screen.dua.emotionsduas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamicapp.domain.duas.emotionsduas.model.EmotionsDuas
import com.islamicapp.domain.duas.emotionsduas.usecase.EmotionsDuasUseCase
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EmotionsDuasViewModel(
    private val emotionsDuasUseCase: EmotionsDuasUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<EmotionsDuas>>>(UIState.Loading())
    val uiState: StateFlow<UIState<List<EmotionsDuas>>> = _uiState

    fun loadEmotionsDuas(fileName: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading()
            try {
                val result = emotionsDuasUseCase(fileName)
                _uiState.value = if (!result.isNullOrEmpty()) {
                    UIState.Success(result)
                } else {
                    UIState.Empty()
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Failure(Throwable(e.localizedMessage ?: "Error loading duas"))
            }
        }
    }
}