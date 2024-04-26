package com.liu.brookl.base.utils

import com.google.gson.Gson

val gson = Gson()
fun Any?.toJson(): String {
    if (this == null) {
        return "{}"
    }
    return gson.toJson(this)
}
inline fun<reified T> String?.fromJson():T?{
    if (this == null) {
        return null
    }
    return gson.fromJson(this,T::class.java)
}