package com.islamicapp.siraathalmusthaqeem.screen.home.components.dailycontent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamicapp.domain.dailycontent.model.DailyDua
import com.islamicapp.domain.dailycontent.model.DailyHadith
import com.islamicapp.domain.dailycontent.usecase.DailyDuaUseCase
import com.islamicapp.domain.dailycontent.usecase.DailyHadithUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DailyContentViewModel(
    private val dailyHadithUseCase: DailyHadithUseCase,
    private val dailyDuaUseCase: DailyDuaUseCase
) : ViewModel() {

    private val _dailyHadith = MutableStateFlow<DailyHadith?>(null)
    val dailyHadith: StateFlow<DailyHadith?> = _dailyHadith.asStateFlow()

    private val _dailyDua = MutableStateFlow<DailyDua?>(null)
    val dailyDua: StateFlow<DailyDua?> = _dailyDua.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadDailyContent()
    }

    private fun loadDailyContent() {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                _dailyHadith.value = dailyHadithUseCase()
                _dailyDua.value = dailyDuaUseCase()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}