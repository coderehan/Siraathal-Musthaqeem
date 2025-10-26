package com.islamicapp.siraathalmusthaqeem.screen.dua.rabbanaduas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamicapp.domain.duas.rabbanaduas.model.RabbanaDuas
import com.islamicapp.domain.duas.rabbanaduas.usecase.RabbanaDuasUseCase
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RabbanaDuasViewModel(
    private val rabbanaDuasUseCase: RabbanaDuasUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<RabbanaDuas>>>(UIState.Loading())
    val uiState: StateFlow<UIState<List<RabbanaDuas>>> get() = _uiState

    fun loadRabbanaDuas() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading()
            try {
                val result = rabbanaDuasUseCase()
                _uiState.value = if (!result.isNullOrEmpty()) {
                    UIState.Success(result)
                } else {
                    UIState.Empty()
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Failure(Throwable(e.localizedMessage ?: "Error loading Rabbana Duas"))
            }
        }
    }
}