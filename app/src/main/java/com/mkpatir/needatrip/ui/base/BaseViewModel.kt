package com.mkpatir.needatrip.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {

    val progressLiveData = MutableLiveData<Boolean>()

    /**
     * Servis çağrısı yapmak için kullanılır.
     * */
    fun <T> callService(
        service: Flow<T>,
        data:(T) -> Unit
    ){
        viewModelScope.launch {
            service.onStart {
                progressLiveData.postValue(true)
            }.collect {
                progressLiveData.postValue(false)
                data(it)
            }
        }
    }
}
