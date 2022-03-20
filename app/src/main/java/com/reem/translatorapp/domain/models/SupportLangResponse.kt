package com.reem.translatorapp.domain.models

data class SupportLangResponse(
    val code: Int = 0,
    val data: Map<String, String>? = null,
    val message: String = ""
)