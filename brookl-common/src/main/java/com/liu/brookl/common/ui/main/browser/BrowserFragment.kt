package com.liu.brookl.common.ui.main.browser

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.liu.brookl.base.manager.createViewModel
import com.liu.brookl.base.BaseFragment
import com.liu.brookl.common.R
import com.liu.brookl.common.databinding.FragmentBrowserBinding
import com.liu.brookl.common.ui.main.browser.web.WebFragment
import com.liu.brookl.common.utils.hideSoftInput
import com.liu.brookl.common.view.SimpleListAdapter

class BrowserFragment : BaseFragment() {
    companion object {
        fun newInstance(): BrowserFragment {
            val args = Bundle()
            val fragment = BrowserFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var mBinding: FragmentBrowserBinding
    private lateinit var mViewModel: BrowserViewModel
    private val webFragment: WebFragment = WebFragment.newInstance()
    override fun getLayoutId(): Int = R.layout.fragment_browser
    override fun initView(view: View) {
        mBinding = FragmentBrowserBinding.bind(view)
        mViewModel = createViewModel()

        mViewModel.url.observe(this) {
            webFragment.setUrl(it)
            childFragmentManager.beginTransaction()
                .replace(mBinding.fragmentContainer.id, webFragment).show(webFragment).commit()
        }
        mBinding.search.setOnEditorActionListener { v, actionId, event ->
            mViewModel.search(v.text.toString())
            v.hideSoftInput()
            mBinding.search.dismissDropDown()
            true
        }
        val adapter = SimpleListAdapter(requireActivity())
        mBinding.search.doAfterTextChanged {
            if (it != null) {
                adapter.setKeyword(it.toString())
                if (!mBinding.search.isPopupShowing) {
                    mBinding.search.showDropDown()
                }

            }
        }
        mBinding.search.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                if (!mBinding.search.isPopupShowing) {
                    mBinding.search.showDropDown()
                }
            }
        }
        mViewModel.searchHistoryList.observe(this) {
            adapter.setDataList(it)
        }
        mBinding.search.setAdapter(adapter)
    }

    override fun onBackPressed(): Boolean {
        if (interceptBackPressed()) {
            return true
        }
        if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack()
            return true
        }
        if (webFragment.isVisible) {
            childFragmentManager.beginTransaction().hide(webFragment).commit()
            return true
        }
        return super.onBackPressed()
    }
}