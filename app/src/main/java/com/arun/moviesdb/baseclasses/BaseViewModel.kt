package com.arun.moviesdb.baseclasses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arun.moviesdb.model.Enums

open class BaseViewModel() : ViewModel() {
    var state = MutableLiveData<Enums.PageState>()

    var errorType = MutableLiveData<Enums.ErrorType>()


    fun setNoNetwork() {
        state.value = Enums.PageState.ERROR
        errorType.value = Enums.ErrorType.NO_INTERNET
    }
}