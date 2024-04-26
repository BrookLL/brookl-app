package com.liu.brookl.base

import android.app.Application
import com.best.android.base.AppManager

abstract class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppManager.init(this, isDebug())
    }

    abstract fun isDebug(): Boolean

}