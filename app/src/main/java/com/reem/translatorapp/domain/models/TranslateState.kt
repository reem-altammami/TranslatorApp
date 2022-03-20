package com.reem.translatorapp.domain.models

data class TranslateState(
    val isLoading: Boolean = false,
    val data: TranslationResponse? = null,
    val message: String = ""
)
