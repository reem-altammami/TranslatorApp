package com.reem.translatorapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reem.translatorapp.domain.models.SupportLangState
import com.reem.translatorapp.domain.models.SupportLangResponse
import com.reem.translatorapp.domain.models.TranslateState
import com.reem.translatorapp.domain.models.TranslationResponse
import com.reem.translatorapp.domain.usecase.GetSupportedLangUseCase
import com.reem.translatorapp.domain.usecase.TranslateUseCase
import com.reem.translatorapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSupportedLangUseCase: GetSupportedLangUseCase,
    private val translateUseCase: TranslateUseCase
): ViewModel(){

    private val _SupportLang_state: MutableStateFlow<SupportLangState> = MutableStateFlow(SupportLangState())
    val supportLangState: StateFlow<SupportLangState> = _SupportLang_state

    private val  _translateState :MutableStateFlow<TranslateState> = MutableStateFlow(TranslateState())
    val translateState : StateFlow<TranslateState> = _translateState


    init {
        getSupportedLang()
    }

    private fun getSupportedLang(){
        viewModelScope.launch {
            getSupportedLangUseCase().onEach { result ->
                when(result){
                    is Resource.Loading -> {
                        _SupportLang_state.value = SupportLangState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _SupportLang_state.value = SupportLangState(data = result.data)
                    }
                    is Resource.Error -> {
                        _SupportLang_state.value = SupportLangState(message = result.message ?: "")
                    }
                }

            }.launchIn(viewModelScope)
        }
    }

     fun translate(text:String,to:String,from:String){
        viewModelScope.launch {
            translateUseCase(text,to,from).onEach { result ->
                when(result) {
                    is Resource.Loading ->{
                        _translateState.value = TranslateState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _translateState.value = TranslateState(data = result.data)
                    }
                    is Resource.Error -> {
                        _translateState.value = TranslateState(message = result.message ?: "")
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}