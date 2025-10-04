package com.islamicapp.siraathalmusthaqeem.screen.hijricalendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.islamicapp.data.common.model.BaseErrorResponse
import com.islamicapp.domain.hijricalendar.model.HijriCalendar
import com.islamicapp.domain.hijricalendar.usecase.HijriCalendarUseCase
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HijriCalendarViewModel(
    private val getHijriCalendarUseCase: HijriCalendarUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<HijriCalendar>>(UIState.Loading())
    val uiState: StateFlow<UIState<HijriCalendar>> get() = _uiState

    fun getCurrentHijriCalendarDate() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading()
            try {
                val result = getHijriCalendarUseCase()
                _uiState.value = UIState.Success(result)
            } catch (e: HttpException) {
                val errorMessage = parseError(e)
                _uiState.value = UIState.Failure(Throwable(errorMessage))
            } catch (e: Exception) {
                _uiState.value = UIState.Failure(Throwable(e.message ?: "Something went wrong"))
            }
        }
    }

    private fun parseError(e: HttpException): String {
        val errorBody = e.response()?.errorBody()?.string()
        return try {
            val parsedError = Gson().fromJson(errorBody, BaseErrorResponse::class.java)
            parsedError.data.ifBlank { "Unknown server error" }
        } catch (parseException: Exception) {
            e.message() ?: "Unknown error"
        }
    }
}