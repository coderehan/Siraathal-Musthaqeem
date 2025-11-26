package com.islamicapp.siraathalmusthaqeem.screen.allah99names

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamicapp.domain.allah99names.model.Allah99Names
import com.islamicapp.domain.allah99names.usecase.Allah99NamesUseCase
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the "Allah 99 Names" screen.
 *
 * Responsibilities:
 * - Fetch the list of Allah’s 99 names from the domain layer (UseCase).
 * - Manage screen UI state (Loading, Success, Empty, Failure).
 * - Handle error parsing (convert API error response into user-friendly text).
 */

class Allah99NamesViewModel(
    private val allah99NamesUseCase: Allah99NamesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<Allah99Names>>>(UIState.Loading())
    val uiState: StateFlow<UIState<List<Allah99Names>>> get() = _uiState

    fun loadAllah99Names() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading()
            try {
                val result = allah99NamesUseCase()
                _uiState.value = if (!result.isNullOrEmpty()) {
                    UIState.Success(result)
                } else {
                    UIState.Empty()
                }
            } catch (e: Exception) {
                _uiState.value =
                    UIState.Failure(Throwable(e.localizedMessage ?: "Error loading names of Allah"))
            }
        }
    }
}
