package com.islamicapp.siraathalmusthaqeem.screen.quran.juz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.islamicapp.data.common.model.BaseErrorResponse
import com.islamicapp.domain.quran.model.Juz
import com.islamicapp.domain.quran.usecase.QuranUseCase
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class JuzViewModel(
    private val quranUseCase: QuranUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<Juz>>>(UIState.Loading())
    val uiState: StateFlow<UIState<List<Juz>>> get() = _uiState

    /**
     * Load Juz detail (1–30)
     */
    fun loadJuzDetail(juzNumber: Int) {
        val current = _uiState.value
        if (current is UIState.Success && current.data.firstOrNull()?.surahNumber == juzNumber) {
            return // Avoid reloading the same juz again
        }

        viewModelScope.launch {
            _uiState.value = UIState.Loading()
            try {
                val result = quranUseCase.getJuzDetail(juzNumber)

                _uiState.value = when {
                    result.isEmpty() -> UIState.Empty()
                    else -> UIState.Success(result)
                }
            } catch (e: HttpException) {
                _uiState.value = UIState.Failure(Throwable(parseError(e)))
            } catch (e: Exception) {
                _uiState.value = UIState.Failure(Throwable(e.message ?: "Something went wrong"))
            }
        }
    }

    /**
     * Parse backend error into readable message
     */
    private fun parseError(e: HttpException): String {
        val errorBody = e.response()?.errorBody()?.string()
        return try {
            val parsedError = Gson().fromJson(errorBody, BaseErrorResponse::class.java)
            parsedError.data ?: "An error occurred"
        } catch (parseException: Exception) {
            e.message() ?: "Unknown error"
        }
    }
}
