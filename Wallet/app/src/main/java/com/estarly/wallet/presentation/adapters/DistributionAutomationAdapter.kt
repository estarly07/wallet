package com.estarly.wallet.presentation.adapters

import android.annotation.SuppressLint
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.estarly.wallet.databinding.ItemDistributionAutomationBinding
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.utils.formatSalary

class DistributionAutomationAdapter(
    var editFields : Boolean = false,
    private val list: List<DistributionOfMoneyModel>,
    val addTextEditChange: (value:DistributionOfMoneyModel, position: Int,text: String)-> Unit,
) : RecyclerView.Adapter<DistributionAutomationAdapter.Holder>() {
    class Holder(val binding: ItemDistributionAutomationBinding, var textWatcher: TextWatcher? = null) : ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
        = Holder(ItemDistributionAutomationBinding.inflate(LayoutInflater.from(parent.context), parent,false))

    override fun getItemCount(): Int = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder.binding){
            val distribution = list[position]
            holder.textWatcher?.let { edtExpectedQuantity.removeTextChangedListener(holder.textWatcher) }
            edtExpectedQuantity.setText(distribution.amountExpectedFormatted)
            txtExpectedQuantity.text = "$ ${ distribution.amountExpectedFormatted.toDoubleOrNull()?.formatSalary() ?: 0}"
            holder.textWatcher = edtExpectedQuantity.addTextChangedListener {
                txtExpectedQuantity.text = "$ ${ it.toString().toDoubleOrNull()?.formatSalary() ?: 0 }"
                distribution.amountExpectedFormatted = it.toString()
                addTextEditChange(distribution,position,it.toString())
            }
            txtTitleDistributionAutomation.text = distribution.name
            imgBackgroundDistribution.setImageResource(distribution.image)
            edtExpectedQuantity.isVisible = editFields
        }
    }
    fun editFields(){
        editFields = !editFields
        notifyDataSetChanged()
    }
    fun getRequiredSalaryFormatted() : String = list.sumOf {distribution -> distribution.amountExpectedFormatted.toDoubleOrNull() ?: 0.0}.formatSalary()
    fun getRequiredSalary() : Double = list.sumOf {distribution -> distribution.amountExpectedFormatted.toDoubleOrNull() ?: 0.0}
}