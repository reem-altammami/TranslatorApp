package com.reem.translatorapp.domain.repository

//import com.reem.translatorapp.domain.models.RequestBody
import com.reem.translatorapp.domain.models.SupportLangResponse
import com.reem.translatorapp.domain.models.TranslationResponse
import okhttp3.RequestBody
import retrofit2.Response

interface Repository {
    suspend fun getSupportedLang():Response<SupportLangResponse>
    suspend fun translate(requestBody: RequestBody):Response<TranslationResponse>
}