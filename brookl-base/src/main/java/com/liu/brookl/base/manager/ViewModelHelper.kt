package com.liu.brookl.base.manager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.liu.brookl.base.BaseActivity
import com.liu.brookl.base.BaseViewModel

inline fun <reified T: BaseViewModel> BaseActivity.createViewModel():T{
    return ViewModelProvider(this)[T::class.java]
}

inline fun <reified T: BaseViewModel> FragmentActivity.createViewModel():T{
    return ViewModelProvider(this)[T::class.java]
}
inline fun <reified T: BaseViewModel> Fragment.createViewModel():T{
    return ViewModelProvider(this)[T::class.java]
}
