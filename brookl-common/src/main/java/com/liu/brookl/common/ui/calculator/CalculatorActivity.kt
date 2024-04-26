package com.liu.brookl.common.ui.calculator

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.liu.brookl.base.manager.createViewModel
import com.liu.brookl.base.BaseActivity
import com.liu.brookl.common.databinding.ActivityCalculatorBinding

class CalculatorActivity : BaseActivity() {
    private lateinit var mBinding: ActivityCalculatorBinding
    private lateinit var mViewModel: CalculatorViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mViewModel = createViewModel()
    }

    override fun initView() {
        mViewModel.record.observe(this) {
            mBinding.textRecord.text = it
        }
        mViewModel.result.observe(this) {
            mBinding.textCalculation.text = it
        }
        val adapter = KeysAdapter()
        mBinding.listKeys.layoutManager = GridLayoutManager(this,4)
        mBinding.listKeys.adapter = adapter
        adapter.setOnKeyClickListener(object :OnKeyClickListener{
            override fun onKeyClick(key: Key) {
                mViewModel.calculate(key)
            }

        })
    }

}