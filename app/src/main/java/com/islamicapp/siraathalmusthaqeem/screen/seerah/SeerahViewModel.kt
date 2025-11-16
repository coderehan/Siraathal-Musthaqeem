package com.islamicapp.siraathalmusthaqeem.screen.seerah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamicapp.domain.seerah.model.Seerah
import com.islamicapp.domain.seerah.usecase.SeerahUseCase
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SeerahViewModel(
    private val seerahUseCase: SeerahUseCase
) : ViewModel() {

    // The state holds a List of SeerahItems
    private val _uiState = MutableStateFlow<UIState<List<Seerah>>>(UIState.Loading())
    val uiState: StateFlow<UIState<List<Seerah>>> get() = _uiState

    /**
     * Fetch Seerah chapter details from a local JSON file.
     * Example fileName: "chapter_1.json"
     */
    fun loadSeerah(fileName: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading()
            try {
                val result = seerahUseCase(fileName)
                _uiState.value = if (!result.isNullOrEmpty()) {
                    UIState.Success(result)
                } else {
                    UIState.Empty()
                }
            } catch (e: Exception) {
                _uiState.value =
                    UIState.Failure(Throwable(e.localizedMessage ?: "Error loading Seerah"))
            }
        }
    }
}