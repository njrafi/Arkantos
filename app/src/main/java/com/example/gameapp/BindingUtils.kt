package com.example.gameapp

import android.net.Uri
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun ImageView.setImageFromUrl(imageUrl: String?) {
    imageUrl?.let {
        val imageUri = Uri.parse(imageUrl).buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imageUri)
            .placeholder(R.drawable.loading_animation)
            .into(this)
    }
}