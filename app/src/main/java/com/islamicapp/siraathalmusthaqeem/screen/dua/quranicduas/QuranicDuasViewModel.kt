package com.islamicapp.siraathalmusthaqeem.screen.dua.quranicduas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamicapp.domain.duas.quranicduas.model.QuranicDuas
import com.islamicapp.domain.duas.quranicduas.usecase.QuranicDuasUseCase
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuranicDuasViewModel(
    private val useCase: QuranicDuasUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<QuranicDuas>>>(UIState.Loading())
    val uiState: StateFlow<UIState<List<QuranicDuas>>> = _uiState

    fun loadQuranicDuas(fileName: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading()
            try {
                // Use the filename to load correct JSON
                val result = useCase.execute(fileName)
                _uiState.value = if (result.isEmpty()) UIState.Empty() else UIState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UIState.Failure(Throwable(e.message ?: "Error loading Quranic Duas"))
            }
        }
    }
}
