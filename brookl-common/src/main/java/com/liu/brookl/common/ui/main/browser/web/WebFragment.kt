package com.liu.brookl.common.ui.main.browser.web

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.liu.brookl.base.manager.createViewModel
import com.liu.brookl.base.BaseFragment
import com.liu.brookl.common.R
import com.liu.brookl.common.databinding.FragmentWebBinding
import com.liu.brookl.common.view.BetterGestureListener
import com.liu.brookl.common.view.setBetterGestureListener
import java.util.LinkedList

class WebFragment : BaseFragment() {
    companion object {
        private const val URL = "_url"
        private const val TAG = "WebView"
        fun newInstance(url: String = ""): WebFragment {
            val args = Bundle()
            args.putString(URL, url)
            val fragment = WebFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mBinding: FragmentWebBinding
    private lateinit var mViewModel: WebViewModel
    override fun getLayoutId(): Int = R.layout.fragment_web
    private var url: String? = null
    private var isNeedReload = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = arguments?.getString(URL)

    }

    override fun initView(view: View) {
        mBinding = FragmentWebBinding.bind(view)
        mViewModel = createViewModel()


        setWebSettings()
        load()

        setGestureListener()


    }

    private fun setGestureListener() {
        mBinding.webView.setBetterGestureListener(activity,null,object :BetterGestureListener(){
            override fun onLeftFling(): Boolean {
                return goForward()
            }

            override fun onRightFling(): Boolean {
                return goBack()
            }
        })


    }

    private fun goForward(): Boolean {
        if (mBinding.webView.canGoForward()) {
            mBinding.webView.goForward()
            return true
        }
        return false
    }

    private fun goBack(): Boolean {
        if (mBinding.webView.canGoBack()) {
            mBinding.webView.goBack()
            return true
        }
        return false
    }

    private fun load() {
        mBinding.webView.clearHistory()
        if (url.isNullOrBlank()) {
            mBinding.webView.loadUrl("https://m.baidu.com/")
        } else if (!URLUtil.isNetworkUrl(url)) {
            mBinding.webView.loadUrl("https://m.baidu.com/s?wd=$url")
        } else {
            mBinding.webView.loadUrl(url!!)
        }
    }

    private fun setWebSettings() {
        mBinding.webView.webChromeClient = object : WebChromeClient() {
            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                super.onShowCustomView(view, callback)

            }

            override fun onHideCustomView() {
                super.onHideCustomView()
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                Log.d(TAG, "newProgress:$newProgress")
                mBinding.progress.setProgress(newProgress, true)
                if (newProgress == 100) {
                    mBinding.progress.hide()
                } else {
                    mBinding.progress.show()
                }
            }

        }
        mBinding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String, favicon: Bitmap?) {
                Log.d(TAG, "url:${url}")
                mViewModel.loadCookie(url)
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String) {
                super.onPageFinished(view, url)
                mViewModel.saveCookie(url)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                Log.e(TAG, request?.url?.toString() ?: "")
                Log.e(TAG, "" + error?.errorCode + "  " + error?.description.toString())
            }
        }

        val setting = mBinding.webView.settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setting.safeBrowsingEnabled = false
        }
        setting.cacheMode = WebSettings.LOAD_DEFAULT
        setting.javaScriptEnabled = true
        setting.useWideViewPort = true
        setting.loadWithOverviewMode = true
        setting.builtInZoomControls = true
        setting.setSupportZoom(false)
        setting.allowFileAccess = true
        setting.domStorageEnabled = true
        setting.databaseEnabled = true
        setting.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

    }

    override fun onResume() {
        super.onResume()
        if (isNeedReload) {
            load()
            isNeedReload = false
        }
    }

    override fun onBackPressed(): Boolean {
        if (mBinding.webView.canGoBack()) {
            mBinding.webView.goBack()
            return true
        }
        return super.onBackPressed()
    }

    fun setUrl(url: String) {
        this.url = url
        val args = arguments
        val oldUrl = args?.getString(URL)
        args?.putString(URL, url)
        arguments = args
        isNeedReload = if (oldUrl.isNullOrBlank()) false else oldUrl != url
        if (isAdded&&isNeedReload){
            load()
            isNeedReload = false
        }
        val list = LinkedList<Integer>()
        list.remove(Integer(1))
    }
}