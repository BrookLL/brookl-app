package com.liu.brookl.common.ui.main.browser.web

import android.util.Log
import android.webkit.CookieManager
import androidx.lifecycle.MutableLiveData
import com.best.android.base.AppManager
import com.liu.brookl.base.BaseViewModel
import com.liu.brookl.common.utils.getCookie
import com.liu.brookl.common.utils.saveCookie
import java.net.URL

class WebViewModel() : BaseViewModel() {

    private val TAG = "BrowserViewModel"


    fun loadCookie(url: String) {
        val host = getUrlHost(url)
        val cookie = AppManager.getApplication().getCookie(getUrlHost(url))
        CookieManager.getInstance().setCookie(host,cookie)
    }

    fun saveCookie(url: String) {
        CookieManager.getInstance().run {
            val host = getUrlHost(url)
            val cookie = getCookie(host)
            Log.d(TAG, "saveCookie:cookie=$cookie")
            AppManager.getApplication().saveCookie(host, cookie)
        }
    }

    private fun getUrlHost(url: String?): String {
        Log.d(TAG, "getUrlHost:url=$url")
        if (url == null) {
            return ""
        }
        return URL(url).host.run {
            Log.d(TAG, "getUrlHost:host=$this")
            this
        }
    }
}