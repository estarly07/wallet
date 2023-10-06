package com.estarly.wallet.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.estarly.wallet.databinding.ItemDistributionAutomationBinding
import com.estarly.wallet.domain.models.DistributionOfMoneyModel

class DistributionAutomationAdapter(
    private val list: List<DistributionOfMoneyModel>,
    val addTextEditChange: (value:DistributionOfMoneyModel, position: Int,text: String)-> Unit,
) : RecyclerView.Adapter<DistributionAutomationAdapter.Holder>() {
    class Holder(val binding: ItemDistributionAutomationBinding) : ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
        = Holder(ItemDistributionAutomationBinding.inflate(LayoutInflater.from(parent.context), parent,false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder.binding){
            val distribution = list[position]
            edtExpectedQuantity.setText(distribution.amountExpectedFormatted)
            edtExpectedQuantity.addTextChangedListener {
                addTextEditChange(distribution,position,it.toString())
            }
            txtTitleDistributionAutomation.text = distribution.name
            imgBackgroundDistribution.setImageResource(distribution.image)
        }
    }
}