package com.reem.translatorapp.domain.usecase

import com.reem.translatorapp.domain.models.SupportLangResponse
import com.reem.translatorapp.domain.repository.Repository
import com.reem.translatorapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSupportedLangUseCase @Inject constructor (private val repository: Repository) {

    suspend operator fun invoke(): Flow<Resource<SupportLangResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getSupportedLang()
            if (response.isSuccessful){
                emit(Resource.Success(response.body()))
            }
        }catch (e: Exception){
            emit(Resource.Error(message = e.toString()))
        }
    }
}