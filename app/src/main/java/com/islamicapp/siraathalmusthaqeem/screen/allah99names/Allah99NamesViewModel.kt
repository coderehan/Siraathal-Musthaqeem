package com.islamicapp.siraathalmusthaqeem.screen.allah99names

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.islamicapp.data.common.model.BaseErrorResponse
import com.islamicapp.domain.allah99names.model.Allah99Names
import com.islamicapp.domain.allah99names.usecase.GetAllah99NamesUseCase
import com.islamicapp.siraathalmusthaqeem.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

/**
 * ViewModel for the "Allah 99 Names" screen.
 *
 * Responsibilities:
 * - Fetch the list of Allah’s 99 names from the domain layer (UseCase).
 * - Manage screen UI state (Loading, Success, Empty, Failure).
 * - Handle error parsing (convert API error response into user-friendly text).
 */
class Allah99NamesViewModel(
    private val getAllah99NamesUseCase: GetAllah99NamesUseCase
) : ViewModel() {

    // Internal state flow that holds the current UI state
    // UIState<T> covers Loading, Success, Empty, and Failure cases.
    private val _uiState = MutableStateFlow<UIState<List<Allah99Names>>>(UIState.Loading())
    // Publicly exposed immutable state flow for UI (Compose) to observe
    val uiState: StateFlow<UIState<List<Allah99Names>>> get() = _uiState

    fun loadAllah99Names() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading()  // Start with loading state
            try {
                // Call domain use case to fetch data
                val result = getAllah99NamesUseCase()

                // Decide UI state based on response
                if (result.isEmpty()) {
                    _uiState.value = UIState.Empty()    // No data
                } else {
                    _uiState.value = UIState.Success(result)    // Data loaded
                }
            } catch (e: HttpException) {
                val errorMessage = parseError(e)
                _uiState.value = UIState.Failure(Throwable(errorMessage))
            } catch (e: Exception) {
                _uiState.value = UIState.Failure(Throwable(e.message ?: "Something went wrong"))
            }
        }
    }

    /**
     * Parses error response body from the backend into a user-friendly message.
     */
    private fun parseError(e: HttpException): String {
        val errorBody = e.response()?.errorBody()?.string()
        return try {
            val parsedError = Gson().fromJson(errorBody, BaseErrorResponse::class.java)
            parsedError.data // Example: "Not found."
        } catch (parseException: Exception) {
            e.message() ?: "Unknown error"
        }
    }
}
