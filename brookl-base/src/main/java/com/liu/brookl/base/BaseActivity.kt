package com.liu.brookl.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.liu.brookl.base.ui.ProgressFragment

private const val PROGRESS_TAG = "Progress"

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initView()
    }

    abstract fun initView()

    override fun onStart() {
        super.onStart()
    }

    /**
     * 显示 Progress [message]
     */
    fun showProgress(message: String?) {
        findOrCreateProgressFragment().run {
            isCancelable = false
            setMessage(message)
        }
    }

    /**
     * 消除 Progress
     * @return true, 消除当前 Progress ; false, 当前无 Progress
     */
    fun dismissProgress(): Boolean {
        findProgressFragment()?.run {
            dismissAllowingStateLoss()
            onDismiss()
            return true
        }
        return false
    }

    private fun findProgressFragment(): ProgressFragment? {
        return supportFragmentManager.findFragmentByTag(PROGRESS_TAG) as ProgressFragment?
    }

    private fun findOrCreateProgressFragment(): ProgressFragment {
        return findProgressFragment()
            ?: run {
                val progress = ProgressFragment()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.add(progress, PROGRESS_TAG)
                if (supportFragmentManager.isStateSaved) {
                    transaction.commitNowAllowingStateLoss()
                } else {
                    transaction.commitNow()
                }
                progress
            }
    }

    open fun onDismiss() {

    }
    override fun onBackPressed() {
        if (dismissProgress()) {
            return
        }
        if (!interceptBackPressed()) {
            super.onBackPressed()
        }
    }

    private fun interceptBackPressed(): Boolean {
        supportFragmentManager.fragments.forEach{
            if (it is OnBackPressedListener){
                if (it.onBackPressed()) {
                    return true
                }
            }
        }
        return false
    }
}