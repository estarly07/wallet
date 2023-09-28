package com.estarly.wallet.presentation.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.estarly.wallet.databinding.ItemDebtBinding
import com.estarly.wallet.domain.models.DebtModel
import com.estarly.wallet.utils.formatSalary

class DebtsAdapter(private val list: List<DebtModel>) : RecyclerView.Adapter<DebtsAdapter.Holder>() {
    class Holder(val binding: ItemDebtBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
        = Holder(ItemDebtBinding.inflate(LayoutInflater.from(parent.context), parent,false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder.binding){
            val debt = list[position]
            txtNameDebtItem.text = debt.name
            if(debt.finished)
                txtMissingAmountDebtItem.text = "Finalizado"
            else
                txtMissingAmountDebtItem.text = debt.missingAmount.formatSalary()
            txtDateDebtItem.text = debt.dateLastPaidFormatted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                progressDebtItem.setProgress(debt.percentagePaid,true)
            }
        }
    }
}