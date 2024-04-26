package com.liu.brookl.base.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.StringRes
import com.best.android.base.BaseDialog
import com.liu.brookl.base.databinding.BaseFragmentProgressBinding

/**
 * Progress Fragment
 */
class ProgressFragment : BaseDialog() {

    private lateinit var mBinding: BaseFragmentProgressBinding
    private var message: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mBinding = BaseFragmentProgressBinding.inflate(inflater, container, false)
        dialog?.window?.let { window ->
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return mBinding.root
    }

    override fun onViewCreated(rootView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(rootView, savedInstanceState)
        mBinding.tvMessage.text = message

        mBinding.btnCancel.visibility = if (isCancelable) View.VISIBLE else View.GONE
        mBinding.btnCancel.setOnClickListener { dismiss() }
    }

    fun setMessage(@StringRes messageRes: Int) {
        this.message = getString(messageRes)
        mBinding.tvMessage.text = message
    }

    fun setMessage(message: String?) {
        this.message = message
        mBinding.tvMessage.text = this.message
    }

    override fun setCancelable(cancelable: Boolean) {
        super.setCancelable(cancelable)
        mBinding.btnCancel.visibility = if (isCancelable) View.VISIBLE else View.GONE
    }
}