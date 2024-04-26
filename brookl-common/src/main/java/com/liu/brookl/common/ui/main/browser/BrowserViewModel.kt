package com.liu.brookl.common.ui.main.browser

import android.webkit.URLUtil
import androidx.lifecycle.MutableLiveData
import com.liu.brookl.base.BaseViewModel
import com.liu.brookl.common.utils.loadHistoryList
import com.liu.brookl.common.utils.saveHistory

class BrowserViewModel() : BaseViewModel() {

    private val TAG = "BrowserViewModel"

    val url = MutableLiveData<String>()
    val searchHistoryList = MutableLiveData<List<String>>()

    init {
        loadSearchHistory()
    }

    fun search(text: String) {
        if (text.contains(".")) {
            url.value = URLUtil.guessUrl(text)
        } else {
            url.value = text
        }
        saveSearchHistory(text)
        loadSearchHistory()
    }

    private fun saveSearchHistory(text: String) {
        saveHistory(text)
    }

    private fun loadSearchHistory() {
        loadHistoryList()?.let {
            searchHistoryList.value = it.toList()
        }
    }
}