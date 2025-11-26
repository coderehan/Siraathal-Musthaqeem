package com.islamicapp.siraathalmusthaqeem.screen.hijricalendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HijriCalendarViewModel(
    private val getHijriCalendarUseCase: CalculateHijriDateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<HijriCalendar>>(UIState.Loading())
    val uiState: StateFlow<UIState<HijriCalendar>> get() = _uiState

    fun getCurrentHijriCalendarDate() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading()
            try {
                val result = getHijriCalendarUseCase()
                _uiState.value = UIState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UIState.Failure(Throwable("Failed to compute Hijri date"))
            }
        }
    }
}