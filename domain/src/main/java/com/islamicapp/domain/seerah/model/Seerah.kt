package com.islamicapp.domain.seerah.model

data class Seerah(
    val topic: String? = null,
    val content: String? = null,
    val reference: Map<String, String>? = null
)