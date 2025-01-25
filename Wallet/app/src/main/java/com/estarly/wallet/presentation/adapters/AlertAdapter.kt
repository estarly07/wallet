package com.estarly.wallet.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.estarly.wallet.databinding.ItemAlertBinding

data class AlertItem(
    val drawable : Int,
    val title    : String,
    val message  : String
)
class AlertAdapter(private val list: List<AlertItem>) : RecyclerView.Adapter<AlertAdapter.Holder>() {
    class Holder(val binding: ItemAlertBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
        = Holder(ItemAlertBinding.inflate(LayoutInflater.from(parent.context), parent,false))

    override fun getItemCount(): Int = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder.binding){
            val data = list[position]
            txtAlert.text = data.title
            txtMessage.text = data.message
            imgBackgroundItemCedetes.setImageResource(data.drawable)

        }
    }
}