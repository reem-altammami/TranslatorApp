package com.reem.translatorapp.di

import com.reem.translatorapp.data.repository.RepositoryImpl
import com.reem.translatorapp.data.retrofitservice.TranslationApi
import com.reem.translatorapp.data.retrofitservice.TranslationApiInterceptor
import com.reem.translatorapp.domain.repository.Repository
import com.reem.translatorapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(TranslationApiInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideTranslationApi(retrofit: Retrofit): TranslationApi {
        return retrofit.create(TranslationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(translationApi: TranslationApi): Repository{
        return RepositoryImpl(
            translateApi = translationApi
        )
    }
}