package com.liu.brookl.common.ui.calculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.liu.brookl.base.utils.getColor
import com.liu.brookl.common.R
import com.liu.brookl.common.databinding.ItemKeyBinding

private val KEYS = listOf(
    Key("AC", true), Key("Delete", true), Key("%", true), Key("/", true),
    Key("7"), Key("8"), Key("9"), Key("x", true),
    Key("4"), Key("5"), Key("6"), Key("-", true),
    Key("1"), Key("2"), Key("3"), Key("+", true),
    Key("+/-", true), Key("0"), Key("."), Key("=", true),
)

class KeysAdapter : RecyclerView.Adapter<KeyViewHolder>() {
    private var onKeyClickListener:OnKeyClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyViewHolder {
        return KeyViewHolder(ItemKeyBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return KEYS.size
    }

    override fun onBindViewHolder(holder: KeyViewHolder, position: Int) {
        val key = KEYS[position]
        holder.binding.keyName.text = key.name
        if (key.isSymbols){
            holder.binding.keyName.setTextColor(getColor(R.color.primary))
        }else{
            holder.binding.keyName.setTextColor(getColor(R.color.text_main_color))
        }
        holder.binding.key.setOnClickListener {
            onKeyClickListener?.onKeyClick(key)
        }
    }

    fun setOnKeyClickListener(onKeyClickListener:OnKeyClickListener):KeysAdapter{
        this.onKeyClickListener = onKeyClickListener
        return this
    }
}

class KeyViewHolder(val binding: ItemKeyBinding) : RecyclerView.ViewHolder(binding.root)

data class Key(val name: String, val isSymbols: Boolean = false)

interface OnKeyClickListener{
    fun onKeyClick(key:Key)
}