package com.ariefrahmanfajri.pilem.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.ariefrahmanfajri.pilem.R
import com.bumptech.glide.Glide

val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
val API_KEY = "?api_key=809e0a1f6d47ca83b2ae328adb012a0b"

fun getCircularProgressBar(context: Context): Drawable {
    val progressBar = CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }

    return progressBar
}

@SuppressLint("UseCompatLoadingForDrawables")
fun ImageView.loadImage(path: String, context: Context) {
    Glide.with(context)
        .load(BASE_IMAGE_URL+path+ API_KEY)
        .placeholder(getCircularProgressBar(context))
        .error(context.getDrawable(R.drawable.ic_image_not_supported_24))
        .into(this)
}

@SuppressLint("UseCompatLoadingForDrawables")
fun ImageView.loadCircularImage(path: String, context: Context) {
    Glide.with(context)
        .load(BASE_IMAGE_URL+path+ API_KEY)
        .placeholder(getCircularProgressBar(context))
        .error(context.getDrawable(R.drawable.ic_image_not_supported_24))
        .circleCrop()
        .into(this)
}