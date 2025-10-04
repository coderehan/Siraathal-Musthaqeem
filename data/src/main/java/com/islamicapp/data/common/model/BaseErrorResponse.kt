package com.islamicapp.data.common.model

/**
 * Represents the standard error response returned by the backend.
 */
data class BaseErrorResponse(
    val code: Int,
    val status: String,
    val data: String
)