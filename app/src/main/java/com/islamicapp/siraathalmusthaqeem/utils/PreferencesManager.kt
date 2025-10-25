package com.islamicapp.siraathalmusthaqeem.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.islamicapp.siraathalmusthaqeem.screen.tasbeeh.model.TasbeehData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// Create DataStore instance tied to Context
val Context.dataStore by preferencesDataStore("app_prefs")

class PreferencesManager(private val context: Context) {

    // Keys
    private val ONBOARDING_KEY = booleanPreferencesKey("onboarding_completed")
    private val PROFILE_KEY = booleanPreferencesKey("profile_completed")
    private val USER_NAME_KEY = stringPreferencesKey("user_name")
    private val IS_DARK_THEME_KEY = booleanPreferencesKey("is_dark_theme")

    // ----- Onboarding -----
    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[ONBOARDING_KEY] = completed
        }
    }

    suspend fun isOnboardingCompleted(): Boolean {
        return context.dataStore.data.map { prefs ->
            prefs[ONBOARDING_KEY] ?: false
        }.first()
    }

    // ----- Profile -----
    suspend fun setProfileCompleted(completed: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PROFILE_KEY] = completed
        }
    }

    suspend fun isProfileCompleted(): Boolean {
        return context.dataStore.data.map { prefs ->
            prefs[PROFILE_KEY] ?: false
        }.first()
    }

    // Save user name in datastore
    suspend fun saveUserName(name: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_NAME_KEY] = name
        }
    }

    // Get user name as Flow
    val userNameFlow: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[USER_NAME_KEY] ?: ""
    }

    // ----- Theme Preference -----
    suspend fun setDarkThemeEnabled(isDark: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[IS_DARK_THEME_KEY] = isDark
        }
    }

    val isDarkThemeFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[IS_DARK_THEME_KEY] ?: false
    }

    // Tasbeeh Preferences
    private val TASBEEH_COUNT_KEY = intPreferencesKey("tasbeeh_count")
    private val TASBEEH_TARGET_KEY = intPreferencesKey("tasbeeh_target")
    private val TASBEEH_SOUND_KEY = booleanPreferencesKey("tasbeeh_sound")
    private val TASBEEH_VIBRATION_KEY = booleanPreferencesKey("tasbeeh_vibration")

    // TASBEEH STORAGE
    val tasbeehDataFlow: Flow<TasbeehData> = context.dataStore.data.map { prefs ->
        TasbeehData(
            count = prefs[TASBEEH_COUNT_KEY] ?: 0,
            target = prefs[TASBEEH_TARGET_KEY] ?: 33, // default value; user can change dynamically
            soundEnabled = prefs[TASBEEH_SOUND_KEY] ?: true,
            vibrationEnabled = prefs[TASBEEH_VIBRATION_KEY] ?: true
        )
    }

    suspend fun saveTasbeehData(data: TasbeehData) {
        context.dataStore.edit { prefs ->
            prefs[TASBEEH_COUNT_KEY] = data.count
            prefs[TASBEEH_TARGET_KEY] = data.target
            prefs[TASBEEH_SOUND_KEY] = data.soundEnabled
            prefs[TASBEEH_VIBRATION_KEY] = data.vibrationEnabled
        }
    }

    suspend fun resetTasbeeh() {
        context.dataStore.edit { prefs ->
            prefs[TASBEEH_COUNT_KEY] = 0
        }
    }

}
