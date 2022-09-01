package com.arun.moviesdb.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arun.moviesdb.api.GeneralRepository
import com.arun.moviesdb.baseclasses.BaseViewModel
import com.arun.moviesdb.model.Enums
import com.arun.moviesdb.model.Movies
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: GeneralRepository) :
    BaseViewModel() {
    val event = MutableLiveData<HomeEvent>()

    init {
        state.value = Enums.PageState.DEFAULT

    }

    val movieList=repository.getMovies().cachedIn(viewModelScope)

//    fun getMovieList(): LiveData<PagingData<Movies>> {
//        return repository.getMovies()
//    }

        enum class HomeEvent {
            SUCCESS, FAILED
        }

    }