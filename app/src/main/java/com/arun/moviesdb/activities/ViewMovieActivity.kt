package com.arun.moviesdb.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.arun.moviesdb.R
import com.arun.moviesdb.baseclasses.BaseActivity
import com.arun.moviesdb.databinding.ActivityViewMovieBinding
import com.arun.moviesdb.utils.Utils.checkInternetAvailability
import com.arun.moviesdb.viewmodels.ViewMovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class ViewMovieActivity : BaseActivity() {
    private lateinit var viewMovieViewModel: ViewMovieViewModel
    private lateinit var binding: ActivityViewMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewMovieViewModel =
            ViewModelProviders.of(this@ViewMovieActivity).get(ViewMovieViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_movie)
        binding.apply {
            viewModel = viewMovieViewModel
        }
        viewMovieViewModel.movieIdLiveData.value = intent.getStringExtra("movieID")
        initViews()
        initObservers()
    }

    private fun initViews() {
        if (!checkInternetAvailability(this)) {
            viewMovieViewModel.setNoNetwork()
        } else {
            viewMovieViewModel.getMovie()
        }

    }

    private fun initObservers() {
        viewMovieViewModel.apply {
            event.observe(this@ViewMovieActivity, {
                when (it) {
                }
            })
            state.observe(this@ViewMovieActivity, { binding.state = it })
        }
        viewMovieViewModel.movieLiveData.observe(this@ViewMovieActivity,
            { binding.moviesModel = it })
    }


}