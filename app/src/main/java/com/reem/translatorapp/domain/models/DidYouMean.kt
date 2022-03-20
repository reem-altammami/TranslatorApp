package com.reem.translatorapp.domain.models

data class DidYouMean(
    val autoCorrected: Boolean = false,
    val value: String = "",
    val didYouMean: Boolean = false
)
