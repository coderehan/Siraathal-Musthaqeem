package com.islamicapp.siraathalmusthaqeem.utils

/**
 * A sealed class representing the different states a UI screen can be in.
 * Used to handle success, error, loading, and empty states in a clean and
 * type-safe way.
 */
sealed class UIState<out T> {

    /**
     * The screen is currently loading its data.
     * Example: show a progress bar/spinner.
     */
    class Loading<out T> : UIState<T>()

    /**
     * Data was successfully loaded for the screen.
     * Example: show the data in the UI.
     */
    data class Success<out T>(val data: T) : UIState<T>()

    /**
     * Data was successfully loaded but is empty.
     * Example: show an empty state message or placeholder view.
     */
    class Empty<out T> : UIState<T>()

    /**
     * Some type of error occurred while loading data.
     * Example: show an error message or retry option.
     */
    data class Failure<out T>(val throwable: Throwable) : UIState<T>()
}

