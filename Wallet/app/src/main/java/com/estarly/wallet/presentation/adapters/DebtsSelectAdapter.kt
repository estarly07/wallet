package com.estarly.wallet.presentation.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.estarly.wallet.databinding.ItemDebtSelectBinding
import com.estarly.wallet.domain.models.DebtModel
import com.estarly.wallet.utils.formatSalary

class DebtsSelectAdapter(private var list: List<DebtModel> = listOf(), private var debtSelect : DebtModel? = null, private val onTap : (DebtModel) -> Unit) : RecyclerView.Adapter<DebtsSelectAdapter.Holder>() {
    class Holder(val binding: ItemDebtSelectBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
        = Holder(ItemDebtSelectBinding.inflate(LayoutInflater.from(parent.context), parent,false))

    override fun getItemCount(): Int = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder.binding){
            val debt = list[position]
            root.setOnClickListener { onTap(debt)}
            imgCheckDebt.isVisible = debt.id == debtSelect?.id
            txtNameDebtItem.text = debt.name
            if(debt.finished)
                txtMissingAmountDebtItem.text = "Finalizado"
            else
                txtMissingAmountDebtItem.text = "$ ${debt.missingAmount.formatSalary()}"
            txtDateDebtItem.text = debt.dateLastPaidFormatted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                progressDebtItem.setProgress(debt.percentagePaid,true)
            }
        }
    }
    fun setList(list : List<DebtModel>){
        this.list  = list
        notifyDataSetChanged()
    }
    fun selectDebt(debt : DebtModel){
        this.debtSelect = debt
        notifyDataSetChanged()
    }
}