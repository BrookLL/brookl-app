package com.liu.brookl.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

open class BetterGestureListener() : GestureDetector.SimpleOnGestureListener() {
    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (abs(velocityX) >= abs(velocityY)) {
            if (velocityX > 0) {
                return onRightFling()
            }
            if (velocityX < 0) {
                return onLeftFling()
            }
        }
        return false
    }

    open fun onLeftFling(): Boolean {

        return false
    }

    open fun onRightFling(): Boolean {
        return false
    }


}

@SuppressLint("ClickableViewAccessibility")
fun View.setBetterGestureListener(
    context: Context? = null,
    handler: Handler? = null,
    listener: BetterGestureListener
) {
    val gestureDetector = GestureDetector(context, listener, handler)
    this.setOnTouchListener { v, event ->
        gestureDetector.onTouchEvent(event)
    }
}
