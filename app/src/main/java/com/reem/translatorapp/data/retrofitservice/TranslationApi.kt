package com.reem.translatorapp.data.retrofitservice

//import com.reem.translatorapp.domain.models.RequestBody
import com.reem.translatorapp.domain.models.SupportLangResponse
import com.reem.translatorapp.domain.models.TranslationResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface TranslationApi {

    @GET("languages")
    suspend fun getSupportedLang(): Response<SupportLangResponse>

    @POST("translate")
//    @FormUrlEncoded
    suspend fun translate(@Body requestBody: RequestBody): Response<TranslationResponse>
}