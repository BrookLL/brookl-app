package com.best.android.base

import android.app.Activity
import android.app.Application
import android.content.res.Resources
import android.os.Bundle
import com.liu.brookl.base.utils.L
import com.liu.brookl.base.BaseActivity
import com.liu.brookl.base.BaseApplication
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object AppManager : CoroutineScope by MainScope() {
    private lateinit var app: BaseApplication
    private val mLifecycleCallbacks = AppLifecycleCallbacks();

    /**
     * 当前正在运行的 Activity，结余 [Activity.onCreate] 与 [Activity.onDestroy] 之间
     */
    private var topActivity: BaseActivity? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        onDismiss()
    }

    fun init(app: BaseApplication, isDebug: Boolean) {
        this.app = app;
        app.registerActivityLifecycleCallbacks(mLifecycleCallbacks)
        L.init(app, isDebug)
    }


    fun addPendingTask(task: ((activity: Activity) -> Any)) {
        mLifecycleCallbacks.addPendingTask(task)
    }

    fun getActivity(): BaseActivity? {
        return topActivity
    }

    fun getApplication(): BaseApplication {
        return app
    }

    fun getResources(): Resources {
        return topActivity?.resources ?: app.resources
    }

    fun launcher(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): DisposableHandle {
        return launch(exceptionHandler, start, block).invokeOnCompletion {
            onDismiss()
        }
    }

    fun launchOnMain(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): DisposableHandle {
        return launch(exceptionHandler, start) {
            withContext(Dispatchers.Main, block)
        }.invokeOnCompletion {
            onDismiss()
        }
    }

    private fun onDismiss() {

    }

    fun <T> async(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> T
    ): DisposableHandle {
        return async(exceptionHandler, start, block).invokeOnCompletion {
            onDismiss()
        }
    }


    private class AppLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

        companion object {
            const val TAG = "AppLifecycleCallbacks"
        }

        var pendingTask: ((activity: Activity) -> Any)? = null
        fun addPendingTask(task: ((activity: Activity) -> Any)?) {
            pendingTask = task
        }


        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            if (activity is BaseActivity) {
                topActivity = activity
            }
            executePendingTask(activity)
        }

        override fun onActivityStarted(activity: Activity) {
            if (activity is BaseActivity) {
                topActivity = activity
            }
            executePendingTask(activity)
        }

        override fun onActivityResumed(activity: Activity) {
            if (activity is BaseActivity) {
                topActivity = activity
            }
            executePendingTask(activity)
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
            if (topActivity == activity) {
                topActivity = null
            }
        }


        private fun executePendingTask(activity: Activity) {
            val task = pendingTask ?: return
            task.invoke(activity)
            pendingTask = null
        }
    }
}