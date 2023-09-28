package com.estarly.wallet.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.estarly.wallet.databinding.ItemProgressIndicatorBinding
import com.estarly.wallet.databinding.ItemRecordBinding
import com.estarly.wallet.domain.models.PercentageModel

class ProgressIndicatorAdapter(val list : List<PercentageModel>) : RecyclerView.Adapter<ProgressIndicatorAdapter.Holder>() {
    class Holder(val binding: ItemProgressIndicatorBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
        = Holder(ItemProgressIndicatorBinding.inflate(LayoutInflater.from(parent.context), parent,false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder.binding){
            progressItem.progress = list[position].percentage
            txtNameProgressItem.text = list[position].name
        }
    }
}