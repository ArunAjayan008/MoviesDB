package com.arun.moviesdb.helpers

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.arun.moviesdb.R
import com.arun.moviesdb.model.Enums
import com.arun.moviesdb.utils.Constants
import com.bumptech.glide.Glide


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(Constants.IMG_URL + imageUrl)
            .error(R.drawable.ic_baseline_local_movies_24)
            .into(view)
    } else {
        Glide.with(view.context)
            .load(R.drawable.ic_baseline_local_movies_24)
            .into(view)
    }
}


@BindingAdapter("setDefaultVisibility")
fun setDefaultVisibility(view: View, state: Enums.PageState) {
    if (state == Enums.PageState.DEFAULT || state == Enums.PageState.SUCCESS)
        view.visibility = View.VISIBLE
    else
        view.visibility = View.GONE
}

@BindingAdapter("setLoadingVisibility")
fun setLoadingVisibility(view: View, state: Enums.PageState) {
    if (state == Enums.PageState.LOADING)
        view.visibility = View.VISIBLE
    else
        view.visibility = View.GONE
}

@BindingAdapter("setErrorVisibility")
fun setErrorVisibility(view: View, state: Enums.PageState) {
    if (state == Enums.PageState.ERROR)
        view.visibility = View.VISIBLE
    else
        view.visibility = View.GONE
}

@BindingAdapter("setEmptyVisibility")
fun setEmptyVisibility(view: View, state: Enums.PageState) {
    if (state == Enums.PageState.EMPTY)
        view.visibility = View.VISIBLE
    else
        view.visibility = View.GONE
}
