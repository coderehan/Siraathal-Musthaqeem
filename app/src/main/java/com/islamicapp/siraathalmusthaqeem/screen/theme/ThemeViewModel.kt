package com.islamicapp.siraathalmusthaqeem.screen.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.islamicapp.siraathalmusthaqeem.utils.PreferencesManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class ThemeViewModel(
    private val prefs: PreferencesManager
) : ViewModel() {


    val isDarkTheme: StateFlow<Boolean> = prefs.isDarkThemeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)


    fun setDarkTheme(isDark: Boolean) {
        viewModelScope.launch {
            prefs.setDarkThemeEnabled(isDark)
        }
    }
}
