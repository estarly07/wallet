package com.estarly.wallet.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.estarly.wallet.databinding.ItemCedetesBinding
import com.estarly.wallet.domain.models.CDTModel
import com.estarly.wallet.utils.formatSalary

class CDTAdapter(val list: List<CDTModel>,val onEdit:(CDTModel)->Unit) : RecyclerView.Adapter<CDTAdapter.Holder>() {
    class Holder(val binding: ItemCedetesBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
        = Holder(ItemCedetesBinding.inflate(LayoutInflater.from(parent.context), parent,false))

    override fun getItemCount(): Int = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder.binding){
            val cdt = list[position]
            imgBackgroundItemCedetes.setImageResource(cdt.image)
            txtDateCDT.text   = cdt.dateLastPaidFormatted
            txtAmountCDT.text = "$ ${cdt.amount.formatSalary()}"
            txtProfitCDT.text = "$ ${ cdt.profit }"
            btnEditCDT.setOnClickListener { onEdit(cdt) }
        }
    }
}