package com.islamicapp.siraathalmusthaqeem.screen.dua.prophetsduas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamicapp.domain.duas.prophetsduas.model.ProphetsDuas
import com.islamicapp.domain.duas.prophetsduas.usecase.ProphetsDuasUseCase
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProphetsDuasViewModel(
    private val useCase: ProphetsDuasUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<ProphetsDuas>>>(UIState.Loading())
    val uiState: StateFlow<UIState<List<ProphetsDuas>>> = _uiState

    fun loadProphetsDuas() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading()
            try {
                val result = useCase.execute()
                _uiState.value = if (result.isEmpty()) UIState.Empty() else UIState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UIState.Failure(Throwable(e.message ?: "Error loading Prophets Duas"))
            }
        }
    }
}