package com.rafalropel.mvvmfavdishes.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rafalropel.mvvmfavdishes.databinding.ItemDishBinding
import com.rafalropel.mvvmfavdishes.model.entities.FavDishEntity

class ItemDishAdapter(private val fragment: Fragment) :
    RecyclerView.Adapter<ItemDishAdapter.ViewHolder>() {

    private var dishes: List<FavDishEntity> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDishAdapter.ViewHolder {

        return ViewHolder(
            ItemDishBinding.inflate(
                LayoutInflater.from(fragment.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ItemDishAdapter.ViewHolder, position: Int) {
        val item = dishes[position]

        Glide.with(fragment)
            .load(item.image)
            .into(holder.ivDishImage)


        holder.tvDishTitle.text = item.title

    }

    override fun getItemCount(): Int {
        return dishes.size

    }

    class ViewHolder(binding: ItemDishBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvDishTitle = binding.tvDishTitle
        val ivDishImage = binding.ivDishImage
    }

    fun dishesList(list: List<FavDishEntity>) {
        dishes = list
        notifyDataSetChanged()
    }
}