package com.liu.brookl.common.ui.main.tools

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.liu.brookl.base.BaseFragment
import com.liu.brookl.common.R
import com.liu.brookl.common.databinding.FragmentToolsBinding

class ToolsFragment : BaseFragment() {
    companion object{
        fun newInstance():ToolsFragment {
            val args = Bundle()
            val fragment = ToolsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mBinding: FragmentToolsBinding
    private lateinit var mViewHolder: ToolsViewHolder
    private val mAdapter: ToolsListAdapter by lazy { ToolsListAdapter() }


    override fun getLayoutId(): Int  = R.layout.fragment_tools

    override fun initView(view: View) {
        mBinding = FragmentToolsBinding.bind(view)
        mBinding.toolsList.layoutManager = GridLayoutManager(activity,3)
        mBinding.toolsList.adapter =mAdapter
    }


}