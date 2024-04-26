package com.liu.brookl.base.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log

object L {

    private const val TAG = "WMS-RF"

    private var loggable = true

    /**
     * 初始化日志配置
     */
    fun init(context: Context, isRelease: Boolean) {
        loggable = !isRelease
        (context.applicationContext as Application).registerActivityLifecycleCallbacks(
            ActivityEventLogger
        )
    }

    /**
     * 设置日志 ID，推荐使用用户 ID
     */
    fun setLogId(id: String) {
    }

    private fun isLoggable(): Boolean {
        return loggable
    }

    fun v(tag: String, message: String) {
        if (isLoggable()) {
            Log.i(tag, message)
        }
    }

    fun d(tag: String, message: String) {
        if (isLoggable()) {
            Log.d(tag, message)
        }
    }

    fun i(tag: String, message: String) {
        if (isLoggable()) {
            Log.i(tag, message)
        }
    }


    fun w(tag: String, message: String? = null, error: Throwable? = null) {
        if (isLoggable()) {
            Log.w(tag, message, error)
        }
    }


    fun e(tag: String, message: String? = null, error: Throwable? = null) {
        if (isLoggable()) {
            Log.e(tag, message, error)
        }
    }

    private object ActivityEventLogger : Application.ActivityLifecycleCallbacks {

        private const val ACTIVITY_CREATED = "ACTIVITY_CREATED"
        private const val ACTIVITY_RESUMED = "ACTIVITY_RESUMED"
        private const val ACTIVITY_PAUSED = "ACTIVITY_PAUSED"
        private const val ACTIVITY_DESTROYED = "ACTIVITY_DESTROYED"

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            val activityName = activity.javaClass.simpleName
            v(TAG, "$activityName\t\tonCreated")
        }

        override fun onActivityStarted(activity: Activity) {
            val activityName = activity.javaClass.simpleName
            v(TAG, "$activityName\t\tonStarted")
        }

        override fun onActivityResumed(activity: Activity) {
            val activityName = activity.javaClass.simpleName
            v(TAG, "$activityName\t\tonStarted")
        }

        override fun onActivityPaused(activity: Activity) {
            val activityName = activity.javaClass.simpleName
            v(TAG, "$activityName\t\tonResumed")
        }

        override fun onActivityStopped(activity: Activity) {
            val activityName = activity.javaClass.simpleName
            v(TAG, "$activityName\t\tonStopped")
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            val activityName = activity.javaClass.simpleName
            v(TAG, "$activityName\t\tonSave")
        }

        override fun onActivityDestroyed(activity: Activity) {
            val activityName = activity.javaClass.simpleName
            v(TAG, "$activityName\t\tonDestroyed")
        }
    }
}