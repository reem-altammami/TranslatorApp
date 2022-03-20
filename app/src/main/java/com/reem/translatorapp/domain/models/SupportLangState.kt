package com.reem.translatorapp.domain.models

data class SupportLangState(
    val isLoading: Boolean = false,
    val data: SupportLangResponse? = null,
    val message: String = ""
)
