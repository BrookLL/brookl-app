package com.liu.brookl.common.utils

import android.content.Context


fun Context.saveCookie(host: String, cookie: String) {
    val sp = getSharedPreferences("_cookie", Context.MODE_PRIVATE)
    sp.edit().putString(host, cookie).apply()
}

fun Context.getCookie(host: String): String {
    val sp = getSharedPreferences("_cookie", Context.MODE_PRIVATE)
    return sp.getString(host, "")?:""
}