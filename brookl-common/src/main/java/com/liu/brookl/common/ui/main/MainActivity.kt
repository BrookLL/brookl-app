package com.liu.brookl.common.ui.main

import android.os.Bundle
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.liu.brookl.base.BaseActivity
import com.liu.brookl.base.BaseFragment
import com.liu.brookl.common.R
import com.liu.brookl.common.databinding.ActivityMainBinding
import com.liu.brookl.common.ui.main.browser.BrowserFragment
import com.liu.brookl.common.ui.main.tools.ToolsFragment
import com.liu.brookl.common.ui.main.user.UserFragment

class MainActivity : BaseActivity() {
    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initView() {
        val browserFragment = BrowserFragment.newInstance()
        val toolsFragment = ToolsFragment.newInstance()
        val userFragment = UserFragment.newInstance()
        var selectedFragment: BaseFragment = browserFragment
        binding.navView.setOnItemSelectedListener {
            selectedFragment = when (it.itemId) {
                R.id.navigation_browser -> browserFragment
                R.id.navigation_dashboard -> toolsFragment
                R.id.navigation_user -> userFragment
                else -> selectedFragment
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, selectedFragment).commit();
            true
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, selectedFragment).commit()

    }
}