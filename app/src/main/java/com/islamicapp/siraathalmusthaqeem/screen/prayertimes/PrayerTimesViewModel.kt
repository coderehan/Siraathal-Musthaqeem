package com.islamicapp.siraathalmusthaqeem.screen.prayertimes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.islamicapp.data.common.model.BaseErrorResponse
import com.islamicapp.domain.prayertimes.model.PrayerTimes
import com.islamicapp.domain.prayertimes.usecase.PrayerTimesUseCase
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PrayerTimesViewModel(
    private val getPrayerTimesUseCase: PrayerTimesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<PrayerTimes>>(UIState.Loading())
    val uiState: StateFlow<UIState<PrayerTimes>> get() = _uiState

    fun getPrayerTimes(date: String = "", latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading()
            try {
                val result = getPrayerTimesUseCase(date, latitude, longitude)
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
