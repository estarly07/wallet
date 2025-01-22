package com.estarly.wallet.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.GONE
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.estarly.wallet.databinding.ItemTransactionBinding
import com.estarly.wallet.domain.models.TransactionModel
import com.estarly.wallet.utils.formatSalary

class TransactionsAdapter(private val listTransactions: List<TransactionModel>,private val onTap : (TransactionModel) -> Unit) : RecyclerView.Adapter<TransactionsAdapter.Holder>() {
    class Holder(val binding: ItemTransactionBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
        = Holder(ItemTransactionBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = listTransactions.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder.binding){
            val transaction = listTransactions[position]
            root.setOnClickListener { onTap(transaction) }
            txtAmountTransaction.text = transaction.amount.formatSalary()
            txtDescriptionTransaction.text = transaction.description
            txtDateTransaction.text = transaction.dateFormatted

        }
    }
}