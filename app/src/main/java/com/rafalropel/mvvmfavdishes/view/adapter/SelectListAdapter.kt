package com.rafalropel.mvvmfavdishes.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rafalropel.mvvmfavdishes.databinding.ItemSelectListBinding
import com.rafalropel.mvvmfavdishes.view.activities.AddUpdateDishActivity

class SelectListAdapter(
    private val activity: Activity,
    private val list: List<String>,
    private val selection: String
) : RecyclerView.Adapter<SelectListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectListAdapter.ViewHolder {
        return ViewHolder(
            ItemSelectListBinding.inflate(
                LayoutInflater.from(activity),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SelectListAdapter.ViewHolder, position: Int) {
        val item = list[position]

        holder.tvText.text = item
        holder.itemView.setOnClickListener {
            if(activity is AddUpdateDishActivity){
                activity.selectedListItem(item, selection)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(binding: ItemSelectListBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvText = binding.tvText
    }
}