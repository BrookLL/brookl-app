package com.liu.brookl.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

abstract class BaseFragment : Fragment(), OnBackPressedListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initView(view: View)

    override fun onBackPressed(): Boolean {
        if (interceptBackPressed()) {
            return true
        }
        if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack()
            return true
        }
        return false
    }

    protected fun interceptBackPressed(): Boolean {
        childFragmentManager.fragments.forEach {
            if (it is OnBackPressedListener) {
                if (it.onBackPressed()) {
                    return true
                }
            }
        }

        return false
    }
}

interface OnBackPressedListener {
    fun onBackPressed(): Boolean
}