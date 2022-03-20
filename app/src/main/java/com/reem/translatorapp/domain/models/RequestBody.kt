package com.reem.translatorapp.domain.models

import com.google.gson.annotations.SerializedName

data class RequestBody(
    val text: String,
    @SerializedName("tl")
    val to: String,
    @SerializedName("sl")
    val from: String
)
