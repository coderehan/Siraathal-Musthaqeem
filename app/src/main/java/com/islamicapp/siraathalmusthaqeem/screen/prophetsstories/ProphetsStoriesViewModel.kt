package com.islamicapp.siraathalmusthaqeem.screen.prophetsstories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamicapp.domain.prophetsstories.model.ProphetsStories
import com.islamicapp.domain.prophetsstories.usecase.ProphetsStoriesUseCase
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProphetsStoriesViewModel(
    private val prophetsStoriesUseCase: ProphetsStoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<ProphetsStories>>(UIState.Loading())
    val uiState: StateFlow<UIState<ProphetsStories>> get() = _uiState

    /**
     * Fetch Prophet story from local JSON file
     * Example fileName: "prophets_stories_1_prophet_adam.json"
     */
    fun loadProphetStory(fileName: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading()
            try {
                val result = prophetsStoriesUseCase(fileName)
                _uiState.value = if (result != null) {
                    UIState.Success(result)
                } else {
                    UIState.Empty()
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Failure(Throwable(e.localizedMessage ?: "Error loading story"))
            }
        }
    }
}
