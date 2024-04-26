package com.liu.brookl.base

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.best.android.base.AppManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel() : ViewModel() {
    protected val exceptionHandler = CoroutineExceptionHandler { _, error ->
        handleException(error)
        onDismiss()
    }

    /**
     * 处理异常
     */
    open fun handleException(error: Throwable) {

    }

    protected fun launcher(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): DisposableHandle {
        return viewModelScope.launch(exceptionHandler, start, block).invokeOnCompletion {
            onDismiss()
        }
    }

    protected fun <T> async(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> T
    ): DisposableHandle {
        return viewModelScope.async(exceptionHandler, start, block).invokeOnCompletion {
            onDismiss()
        }
    }

    /**
     * 用于处理并发批量操作同一类的网络请求
     * 如批量上传图片
     */
    protected suspend fun <R, T> asyncAllTask(
        requestParams: MutableList<R>,
        handler: suspend (value: R) -> T?
    ): List<T> {
        return withContext(Dispatchers.IO) {
            val requestAsyncs = mutableListOf<Deferred<T>>()
            requestParams.forEach { value ->
                val task = async {
                    return@async handler.invoke(value)
                }
                requestAsyncs.add(task as Deferred<T>)
            }
            return@withContext awaitAll(*requestAsyncs.toTypedArray())
        }
    }

    open fun onDismiss() {
        dismissProgress();
    }

    protected fun showProgress(text: String? = null) {
    }

    protected fun dismissProgress() {
    }
}

