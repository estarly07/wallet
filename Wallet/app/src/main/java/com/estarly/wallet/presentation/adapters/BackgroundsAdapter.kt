package com.estarly.wallet.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.estarly.wallet.databinding.ItemBackgroundBinding
import com.estarly.wallet.databinding.ItemCedetesBinding
import com.estarly.wallet.databinding.ItemGoalBinding

class BackgroundsAdapter(
    private val resources : List<Int>,
    private val onSelect  : (Int) -> Unit
) : RecyclerView.Adapter<BackgroundsAdapter.Holder>() {
    private var backgroundSelectIndex : Int ? = null
    class Holder(val binding: ItemBackgroundBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
        = Holder(ItemBackgroundBinding.inflate(LayoutInflater.from(parent.context), parent,false))

    override fun getItemCount(): Int = resources.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder.binding){
            root.setOnClickListener {
                backgroundSelectIndex = position
                onSelect(resources[position])
                notifyDataSetChanged()
            }
            imgCheckBackground.visibility =if(backgroundSelectIndex == position) View.VISIBLE else View.GONE
            itemBackground.setImageResource(resources[position])
        }
    }
}