package com.estarly.wallet.presentation.adapters

import android.content.res.ColorStateList
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.estarly.wallet.R
import com.estarly.wallet.databinding.ItemGraphicBinding
import com.estarly.wallet.domain.models.ItemGraphic

class GraphicItemsAdapter(val listItems: List<ItemGraphic>) : RecyclerView.Adapter<GraphicItemsAdapter.Holder>() {
    class Holder(val binding: ItemGraphicBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
        =  Holder(ItemGraphicBinding.inflate(LayoutInflater.from(parent.context), parent,false))

    override fun getItemCount(): Int = listItems.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder.binding){
            txtItemGraphic.text = listItems[position].name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                indicatorItemGraphic.backgroundTintList =ColorStateList.valueOf(root.context.getColor(listItems[position].color))
            }
        }
    }
}