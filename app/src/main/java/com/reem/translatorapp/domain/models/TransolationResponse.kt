package com.reem.translatorapp.domain.models

import com.google.gson.annotations.SerializedName

data class TranslationResponse(
    val code: Int = 0,
    val data: Data = Data(),
    val message: String = ""
)
