package com.islamicapp.siraathalmusthaqeem.screen.dua.masnoonduas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamicapp.domain.duas.masnoonduas.model.MasnoonDuasTopic
import com.islamicapp.domain.duas.masnoonduas.usecase.MasnoonDuasUseCase
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MasnoonDuasViewModel(
    private val masnoonDuasUseCase: MasnoonDuasUseCase
) : ViewModel() {

    // State for loading/success/failure of topics list
    private val _uiState = MutableStateFlow<UIState<List<MasnoonDuasTopic>>>(UIState.Loading())
    val uiState: StateFlow<UIState<List<MasnoonDuasTopic>>> get() = _uiState

    /**
     * Loads all Masnoon Duas topics
     */
    fun loadMasnoonDuasTopics() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading()
            try {
                val result = masnoonDuasUseCase()
                _uiState.value = if (!result.isNullOrEmpty()) {
                    UIState.Success(result)
                } else {
                    UIState.Empty()
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Failure(
                    Throwable(e.localizedMessage ?: "Error loading Masnoon Duas")
                )
            }
        }
    }
}