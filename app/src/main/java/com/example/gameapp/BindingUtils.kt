package com.example.gameapp

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.gameapp.repository.GameApiStatus
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

@BindingAdapter("imageUrl")
fun ImageView.setImageFromUrl(imageUrl: String?) {
    val shimmer = Shimmer
        .AlphaHighlightBuilder()
        .setDuration(1500) // how long the shimmering animation takes to do one full sweep
        .setBaseAlpha(0.95f) //the alpha of the underlying children
        .setHighlightAlpha(0.90f) // the shimmer alpha amount
        .setDirection(Shimmer.Direction.TOP_TO_BOTTOM)
        .setAutoStart(true)
        .build()
    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }
    imageUrl?.let {
        val imageUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imageUri)
            .placeholder(shimmerDrawable)
            .error(R.drawable.ic_connection_error)
            .into(this)
    }
}

@BindingAdapter("apiStatus")
fun ImageView.setApiStatus(status: GameApiStatus?) {
    if (status != null)
        Log.i("ImageGameApiStatus", status.name)
    when (status) {
        GameApiStatus.LOADING -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.loading_animation)
        }
        GameApiStatus.ERROR -> {
            visibility = View.VISIBLE
            setImageResource(R.drawable.ic_connection_error)
        }
        else -> visibility = View.GONE
    }
}

@BindingAdapter("apiStatus")
fun ProgressBar.setApiStatus(status: GameApiStatus?) {
    if (status == GameApiStatus.LOADING) {
        visibility = View.VISIBLE
        animate()
    } else {
        visibility = View.GONE
    }
}

@BindingAdapter("goneUnless")
fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}