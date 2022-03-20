package com.reem.translatorapp.data.retrofitservice

import com.reem.translatorapp.utils.Constants.API_KEY
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TranslationApiInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        val newRequest: Request = originalRequest.newBuilder()
            .addHeader("x-rapidapi-key", API_KEY)
            .addHeader("x-rapidapi-host", "google-translate20.p.rapidapi.com")
            .build()

        return chain.proceed(newRequest)
    }
}