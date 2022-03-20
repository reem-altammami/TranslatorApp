package com.reem.translatorapp.domain.models

import com.google.gson.annotations.SerializedName

data class Data(
    val translation: String = "",
    @SerializedName("text")
    val corrected: DidYouMean = DidYouMean()
)
