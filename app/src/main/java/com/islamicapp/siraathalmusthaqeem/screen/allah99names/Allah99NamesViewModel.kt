package com.islamicapp.siraathalmusthaqeem.screen.allah99names

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamicapp.siraathalmusthaqeem.allah99names.model.Allah99Names
import com.islamicapp.siraathalmusthaqeem.allah99names.usecase.GetAllah99NamesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Allah99NamesViewModel(
    private val getAllah99NamesUseCase: GetAllah99NamesUseCase
) : ViewModel() {

    private val _allahNames = MutableStateFlow<List<Allah99Names>>(emptyList())
    val allahNames: StateFlow<List<Allah99Names>> get() = _allahNames

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun loadAllah99Names() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = getAllah99NamesUseCase()
                _allahNames.value = result
            } catch (e: Exception) {
                _error.value = e.message ?: "Something went wrong"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
