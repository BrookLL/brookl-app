package com.liu.brookl.base.manager

import android.widget.ImageView
import com.best.android.base.AppManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File


object ImageLoaderManager {
    fun load(url: String, imageView: ImageView) {
        Glide.with(AppManager.getApplication()).load(url).into(imageView)
    }

    fun load(file: File, imageView: ImageView) {
        Glide.with(AppManager.getApplication()).load(file).into(imageView)
    }

    private fun getOptions(): RequestOptions {
        return RequestOptions().apply {
//            placeholder()
//            error()
        }
    }
}

fun ImageView.setImageUrl(url: String) {
    ImageLoaderManager.load(url, this)
}

fun ImageView.setImagePath(path: String) {
    ImageLoaderManager.load(File(path), this)
}