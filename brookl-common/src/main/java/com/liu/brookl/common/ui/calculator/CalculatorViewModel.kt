package com.liu.brookl.common.ui.calculator

import androidx.lifecycle.MutableLiveData
import com.liu.brookl.base.BaseViewModel
import org.w3c.dom.Node
import java.math.BigDecimal

class CalculatorViewModel() : BaseViewModel() {

    val record = MutableLiveData<String>()
    val result = MutableLiveData<String>()
    private val first = CNode("",null,null)
    private var current = first
    init {
        load()//ViewModel初始化时调用
    }

    fun load() {
        showProgress()
        launcher {
        }
    }

    fun calculate(key: Key) {
        if (key.isSymbols) {
            current.next= CNode(key.name,current,null)
            current = current.next!!
        }else{
            current.num += key.name
        }
        HashMap<String,String>().contains("")
    }

  data  class CNode(var num:String,var pre:CNode?,var next:CNode?)
}