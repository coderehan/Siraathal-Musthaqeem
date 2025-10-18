package com.islamicapp.siraathalmusthaqeem.screen.hadith

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamicapp.core.utils.HadithCollectionType
import com.islamicapp.domain.hadith.model.Hadith
import com.islamicapp.domain.hadith.usecase.HadithUseCase
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HadithViewModel(
    private val getHadithUseCase: HadithUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<Hadith>>>(UIState.Loading())
    val uiState: StateFlow<UIState<List<Hadith>>> get() = _uiState

    // Cache to store hadiths per collection type (prevents recomposition issues)
    private val hadithCache = mutableMapOf<HadithCollectionType, List<Hadith>>()

    fun loadHadiths(collection: HadithCollectionType) {
        viewModelScope.launch {
            // Show loading while fetching (only if not cached yet)
            if (!hadithCache.containsKey(collection)) {
                _uiState.value = UIState.Loading()
            }

            // Return cached data immediately if available
            hadithCache[collection]?.let {
                _uiState.value = UIState.Success(it)
                return@launch
            }

            try {
                // Fetch from repository via use case
                val result = getHadithUseCase(collection)

                // Cache result for later
                if (result.isNotEmpty()) {
                    hadithCache[collection] = result
                    _uiState.value = UIState.Success(result)
                } else {
                    _uiState.value = UIState.Empty()
                }

            } catch (e: HttpException) {
                val message = parseError(e)
                _uiState.value = UIState.Failure(Throwable(message))
            } catch (e: Exception) {
                _uiState.value = UIState.Failure(Throwable(e.message ?: "Something went wrong"))
            }
        }
    }

    private fun parseError(e: HttpException): String {
        return when (e.code()) {
            404 -> "Hadith collection not found."
            500 -> "Server error. Please try again later."
            else -> "Unexpected error: ${e.message()}"
        }
    }
}
