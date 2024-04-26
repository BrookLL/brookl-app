package com.liu.brookl.common.view

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

class SimpleListAdapter(context: Context) :
    ArrayAdapter<String>(context, android.R.layout.simple_list_item_1) {

    private val originalDataList = mutableListOf<String>()
    private var mFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            val results = Filter.FilterResults()
            if (constraint.isNullOrBlank()) {
                results.values = originalDataList
                results.count = originalDataList.size
            }else{
                val filterList = originalDataList.filter { it.contains(constraint) }
                results.values = filterList
                results.count = filterList.size
            }
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults) {
            clear()
            addAll(results.values)
        }
    }

    private fun addAll(values: Any?) {
        if (values != null && values is Collection<*>) {
            super.addAll(values.map { it as String })
        }
    }

    fun setDataList(dataList: List<String>) {
        originalDataList.clear()
        originalDataList.addAll(dataList)
        setKeyword("")
    }

    fun setKeyword(keyword: String) {
        filter.filter(keyword)
    }

    override fun getFilter(): Filter {
        return mFilter
    }
}

