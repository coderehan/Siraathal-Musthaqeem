package com.islamicapp.siraathalmusthaqeem.screen.dua.masnoonduas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamicapp.domain.duas.masnoonduas.model.MasnoonDuas
import com.islamicapp.domain.duas.masnoonduas.usecase.MasnoonDuasUseCase
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MasnoonDuasViewModel(
    private val masnoonDuasUseCase: MasnoonDuasUseCase
) : ViewModel() {

    // State for loading/success/failure of the Duas list
    private val _uiState = MutableStateFlow<UIState<List<MasnoonDuas>>>(UIState.Loading())
    val uiState: StateFlow<UIState<List<MasnoonDuas>>> get() = _uiState

    // State for tracking the currently expanded dua index (single selection)
    private val _expandedIndex = MutableStateFlow<Int?>(null)
    val expandedIndex: StateFlow<Int?> get() = _expandedIndex

    /**
     * Toggles the expanded state for a given index.
     * If the clicked index is already expanded, it collapses it.
     * Otherwise, it expands the new index and collapses the previous one (single selection model).
     */
    fun toggleExpansion(index: Int) {
        _expandedIndex.value = if (_expandedIndex.value == index) {
            null // Collapse if already expanded
        } else {
            index // Expand new item
        }
    }

    /**
     * Loads the Masnoon Duas from the repository.
     */
    fun loadMasnoonDuas() {
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
                _uiState.value =
                    UIState.Failure(Throwable(e.localizedMessage ?: "Error loading Masnoon Duas"))
            }
        }
    }
}