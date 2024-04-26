package com.liu.brookl.common.ui.main.tools

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.liu.brookl.base.BaseViewModel

class ToolsViewModel(): BaseViewModel()  {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}