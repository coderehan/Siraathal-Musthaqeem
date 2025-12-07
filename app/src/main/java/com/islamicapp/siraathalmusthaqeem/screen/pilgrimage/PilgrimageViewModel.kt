package com.islamicapp.siraathalmusthaqeem.screen.pilgrimage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamicapp.domain.pilgrimage.model.Pilgrimage
import com.islamicapp.domain.pilgrimage.usecase.PilgrimageUseCase
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PilgrimageViewModel(
    private val pilgrimageUseCase: PilgrimageUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<Pilgrimage>>(UIState.Loading())
    val uiState: StateFlow<UIState<Pilgrimage>> get() = _uiState

    /**
     * Load Pilgrimage data from a local JSON file.
     * Example fileName: "umrah.json" or "hajj.json"
     */
    fun loadPilgrimage(fileName: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading()
            try {
                val result = pilgrimageUseCase(fileName)
                _uiState.value = if (result != null) {
                    UIState.Success(result)
                } else {
                    UIState.Empty()
                }
            } catch (e: Exception) {
                _uiState.value =
                    UIState.Failure(Throwable(e.localizedMessage ?: "Error loading Pilgrimage"))
            }
        }
    }
}