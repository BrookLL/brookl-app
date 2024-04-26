package com.liu.brookl.common.utils

import android.content.Context
import com.best.android.base.AppManager
import com.liu.brookl.base.utils.fromJson
import com.liu.brookl.base.utils.toJson

private const val FILE_NAME = "_history"
private const val KEY = "_history_list"
fun saveHistory(keyword: String) {
    val sp = AppManager.getApplication().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    val keywordList = mutableListOf<String>()

    loadHistoryList()?.let {
        keywordList.addAll(it)
    }
    keywordList.remove(keyword)
    keywordList.add(0, keyword)
    if (keywordList.size > 20) {
        keywordList.removeLast()
    }
    sp.edit().putString(KEY, keywordList.toJson()).apply()
}

fun loadHistoryList(): List<String>? {
    val sp = AppManager.getApplication().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    val data = sp.getString(KEY, "") ?: ""
    return data.fromJson<MutableList<String>>()
}