package com.arun.moviesdb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arun.moviesdb.R
import com.arun.moviesdb.databinding.AdapterMovieslistBinding
import com.arun.moviesdb.helpers.bindings
import com.arun.moviesdb.interfaces.RecyclerItemClickListener
import com.arun.moviesdb.model.Movies

class MovieHomeAdapter(val listener: RecyclerItemClickListener) :
    PagingDataAdapter<Movies, MovieHomeAdapter.MovieViewhodler>(MovieDiff) {


    override fun onBindViewHolder(holder: MovieHomeAdapter.MovieViewhodler, position: Int) {
        getItem(position)?.let { (holder as? MovieViewhodler)?.bindData(movieNode = it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieHomeAdapter.MovieViewhodler {
        val inflater =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_movieslist, parent, false)
        return MovieViewhodler(inflater)
    }

    inner class MovieViewhodler(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: AdapterMovieslistBinding by bindings(view)
        fun bindData(movieNode: Any) {
            if (movieNode is Movies) {
                binding.apply {
                    node = movieNode
                    root.setOnClickListener(View.OnClickListener {
                        listener.onListItemClicked(movieNode.id)
                    })
                }
            }
        }
    }


    object MovieDiff : DiffUtil.ItemCallback<Movies>() {
        override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem == newItem
        }

    }

}