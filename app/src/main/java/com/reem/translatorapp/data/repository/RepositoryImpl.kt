package com.reem.translatorapp.data.repository

import com.reem.translatorapp.data.retrofitservice.TranslationApi
//import com.reem.translatorapp.domain.models.RequestBody
import com.reem.translatorapp.domain.models.SupportLangResponse
import com.reem.translatorapp.domain.models.TranslationResponse
import com.reem.translatorapp.domain.repository.Repository
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val translateApi: TranslationApi
): Repository {
    override suspend fun getSupportedLang(): Response<SupportLangResponse> = translateApi.getSupportedLang()
    override suspend fun translate(requestBody: RequestBody): Response<TranslationResponse> = translateApi.translate(requestBody)

}