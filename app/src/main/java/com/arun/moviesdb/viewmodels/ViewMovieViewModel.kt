package com.arun.moviesdb.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import com.arun.moviesdb.api.GeneralRepository
import com.arun.moviesdb.baseclasses.BaseViewModel
import com.arun.moviesdb.model.Enums
import com.arun.moviesdb.model.MovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class ViewMovieViewModel @Inject constructor(private val repository: GeneralRepository) :
    BaseViewModel() {
    val event = MutableLiveData<ViewMovieEvent>()
    val movieIdLiveData = MutableLiveData<String>()
    var movieLiveData = MutableLiveData<MovieDetails>()

    init {
        state.value = Enums.PageState.DEFAULT
    }

    fun getMovie() {
        repository.getMovieDetails(
            movieIdLiveData.value.toString(),
            onResponse = { response_status, message, response ->
                if (response_status == true) {
                    if (response != null) {
                        movieLiveData.value = response
                    }
                } else {
                    state.value = Enums.PageState.ERROR
                }

            })
    }


    enum class ViewMovieEvent {
        SUCCESS, FAILED
    }
}