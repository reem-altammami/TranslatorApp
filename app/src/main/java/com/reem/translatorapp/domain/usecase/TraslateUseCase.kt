package com.reem.translatorapp.domain.usecase

import com.reem.translatorapp.domain.models.RequestBody
import com.reem.translatorapp.domain.models.TranslationResponse
import com.reem.translatorapp.domain.repository.Repository
import com.reem.translatorapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class TranslateUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(
        text: String,
        to: String,
        from: String
    ): Flow<Resource<TranslationResponse>> = flow {
        try {
            emit(Resource.Loading())
//            val body = RequestBody(
//                text, to, from
//            )
            val mediaType = "application/x-www-form-urlencoded".toMediaType()
            val body = "text=$text&tl=$to&sl=$from".toRequestBody(mediaType)
            val response = repository.translate(body)
            if (response.isSuccessful) {
                emit(Resource.Success(response.body()))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.toString()))
        }


    }
}