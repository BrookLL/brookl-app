package com.liu.brookl.common.ui.main

import androidx.navigation.fragment.NavHostFragment
import com.liu.brookl.base.OnBackPressedListener

class BrookLNavHostFragment(): NavHostFragment(),OnBackPressedListener{

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

    private fun interceptBackPressed(): Boolean {
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
