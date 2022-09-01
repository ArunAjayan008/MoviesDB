package com.arun.moviesdb.activities

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.arun.moviesdb.R
import com.arun.moviesdb.adapters.MovieHomeAdapter
import com.arun.moviesdb.baseclasses.BaseActivity
import com.arun.moviesdb.databinding.ActivityMainBinding
import com.arun.moviesdb.interfaces.RecyclerItemClickListener
import com.arun.moviesdb.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : BaseActivity(), RecyclerItemClickListener {
    private lateinit var mainViewmodel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private var moviesAdapter: MovieHomeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewmodel =
            ViewModelProviders.of(this@MainActivity).get(MainActivityViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            viewModel = mainViewmodel
        }
        initViews()
        initObservers()
        initRecyclers()
    }


    private fun initObservers() {
        mainViewmodel.apply {
            event.observe(this@MainActivity, {
                when (it) {

                }
            })
            state.observe(this@MainActivity, { binding.state = it })
            errorType.observe(this@MainActivity, { binding.errorType = it })
        }
    }

    private fun initViews() {

    }

    private fun initRecyclers() {
        binding.recyclerMovieslist.apply {
            moviesAdapter = MovieHomeAdapter(this@MainActivity)
            adapter = moviesAdapter
        }
        lifecycleScope.launch {
            mainViewmodel.movieList.observe(this@MainActivity) {
                it?.let {
                    moviesAdapter?.submitData(lifecycle, it)
                }
            }
        }


    }

    override fun onListItemClicked(item: Any) {
        val intent = Intent(this@MainActivity, ViewMovieActivity::class.java)
        intent.putExtra("movieID", item.toString())
        startActivity(intent)
    }
}