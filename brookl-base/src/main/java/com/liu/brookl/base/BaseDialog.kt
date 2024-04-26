package com.best.android.base

import android.os.Bundle
import android.view.Gravity
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatDialogFragment

abstract class BaseDialog : AppCompatDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.let { window ->
            val layoutParams = window.attributes
            window.setBackgroundDrawableResource(getBackgroundDrawableResource())
            getWidthScaleInScreen()?.let {
                val width = (resources.displayMetrics.widthPixels * it).toInt()
                layoutParams.width = width
            }
            getHeightScaleInScreen()?.let {
                val height =
                    (resources.displayMetrics.heightPixels * it).toInt()
                layoutParams.height = height
            }

            layoutParams.gravity = getGravityInScreen()
            window.attributes = layoutParams
        }
    }

    /**
     * 继承以设置Dialog在屏幕中的宽度比例
     */
    protected open fun getWidthScaleInScreen(): Float? = null

    /**
     * 继承以设置Dialog在屏幕中的高度比例
     */
    protected open fun getHeightScaleInScreen(): Float? = null

    /**
     * 继承以设置Dialog在屏幕中的Gravity
     */
    protected open fun getGravityInScreen(): Int = Gravity.CENTER

    /**
     * 继承以设置Dialog窗口背景
     */
    @DrawableRes
    protected open fun getBackgroundDrawableResource(): Int =
        android.R.drawable.screen_background_light_transparent

}