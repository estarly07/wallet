package com.estarly.wallet.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.estarly.wallet.databinding.ItemDistributionBinding
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.utils.formatSalary

class DistributionSelectAdapter(
    private val list: List<DistributionOfMoneyModel>,
    val onClick: (value:DistributionOfMoneyModel?, position: Int?)-> Unit,
) : RecyclerView.Adapter<DistributionSelectAdapter.Holder>() {
    var positionCheck : Int? = null
    class Holder(val binding: ItemDistributionBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
        = Holder(ItemDistributionBinding.inflate(LayoutInflater.from(parent.context), parent,false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder.binding){
            root.setOnClickListener{
                if (positionCheck == position) {
                    positionCheck = null
                    onClick(null,null)
                }else{
                    onClick(list[position],position)
                    positionCheck = position
                }
                notifyDataSetChanged()
            }
            imgCheckDistribution.visibility = if(positionCheck!= null && positionCheck == position) View.VISIBLE else View.GONE
            imgAddDistribution.visibility = View.GONE
            itemDistribution.visibility   = View.VISIBLE
            btnSettingsItemDistribution.visibility = View.GONE
            txtTitleItemDistribution.text = list[position].name
            txtAmountItemDistribution.text = list[position].amountSaved.formatSalary()
            imgItemDistribution.setImageResource(list[position].image)
        }
    }
}