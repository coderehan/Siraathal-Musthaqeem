package com.islamicapp.siraathalmusthaqeem.screen.quran.surah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.islamicapp.data.common.model.BaseErrorResponse
import com.islamicapp.domain.quran.model.Surah
import com.islamicapp.domain.quran.usecase.QuranUseCase
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SurahViewModel(
    private val quranUseCase: QuranUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<Surah>>>(UIState.Loading())
    val uiState: StateFlow<UIState<List<Surah>>> get() = _uiState

    /**
     * Load list of all Surahs (1–114)
     */
    fun loadSurahList() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading()
            try {
                val result = quranUseCase.getSurahList()

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
     * Load Surah detail (ayahs) by number (1–114)
     */
    fun loadSurahDetail(surahNumber: Int) {
        val current = _uiState.value
        if (current is UIState.Success && current.data.firstOrNull()?.surahNumber == surahNumber) {
            return // Prevent reloading same surah again
        }
        viewModelScope.launch {
            _uiState.value = UIState.Loading()
            try {
                val result = quranUseCase.getSurahDetail(surahNumber)

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
