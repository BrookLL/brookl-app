package com.liu.brookl.common.ui.main.tools

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.liu.brookl.base.BaseActivity
import com.liu.brookl.common.R
import com.liu.brookl.common.databinding.ItemToolBinding
import com.liu.brookl.common.ui.calculator.CalculatorActivity

private val TOOLS = listOf<ToolEntity>(
    ToolEntity(
        R.drawable.icon_calculate,
        R.string.calculate,
        CalculatorActivity::class.java
    )
)

class ToolsListAdapter(private val toolsList: List<ToolEntity> = TOOLS) :
    RecyclerView.Adapter<ToolsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolsViewHolder {
        return ToolsViewHolder(
            ItemToolBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = toolsList.size

    override fun onBindViewHolder(holder: ToolsViewHolder, position: Int) {
        val data = toolsList[position]
        holder.binding.image.setImageResource(data.iconResId)
        holder.binding.name.setText(data.nameResId)
        holder.binding.root.setOnClickListener {
            try {
                val intent = Intent(it.context, data.clazz)
                it.context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}

class ToolsViewHolder(val binding: ItemToolBinding) : RecyclerView.ViewHolder(binding.root)

data class ToolEntity(
    @DrawableRes val iconResId: Int,
    @StringRes val nameResId: Int,
    val clazz: Class<out BaseActivity>
)