package com.liu.brookl.common.ui.main.user

import android.os.Bundle
import android.view.View
import com.liu.brookl.base.BaseFragment
import com.liu.brookl.common.R
import com.liu.brookl.common.databinding.FragmentUserBinding

class UserFragment : BaseFragment() {
    companion object{
        fun newInstance(): UserFragment {
            val args = Bundle()
            val fragment = UserFragment()
            fragment.arguments = args
            return fragment
        }
    }
    private lateinit var mBinding:FragmentUserBinding
    override fun getLayoutId(): Int  = R.layout.fragment_user

    override fun initView(view: View) {
        mBinding = FragmentUserBinding.bind(view)
    }


}