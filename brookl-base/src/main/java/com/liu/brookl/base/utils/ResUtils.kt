package com.liu.brookl.base.utils

import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.best.android.base.AppManager

fun getString(@StringRes resId: Int): String {
    return AppManager.getResources().getString(resId)
}

fun toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    toast(getString(resId), duration)
}

fun toast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    showToast(message, duration)
}



private var toast: Toast? = null
private fun showToast(message: String?, duration: Int) {
    if (message.isNullOrBlank()) {
        return
    }

    AppManager.launchOnMain {
        toast?.cancel()
        toast = Toast.makeText(AppManager.getApplication(), message, duration)
            .apply { show() }
    }
}


fun getColor(@ColorRes colorId:Int):Int{
    return AppManager.getResources().getColor(colorId)
}