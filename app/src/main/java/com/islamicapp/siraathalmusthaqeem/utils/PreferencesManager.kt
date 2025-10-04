package com.islamicapp.siraathalmusthaqeem.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
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
}
