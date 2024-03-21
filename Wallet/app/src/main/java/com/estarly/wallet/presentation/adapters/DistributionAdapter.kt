package com.estarly.wallet.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.estarly.wallet.databinding.ItemDistributionBinding
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.utils.formatSalary

class DistributionAdapter(
    private val list: List<DistributionOfMoneyModel>,
    val onClick: (value:DistributionOfMoneyModel, position: Int)-> Unit,
    val onClickCreate: ()-> Unit,
    val onDelete : (value:DistributionOfMoneyModel, position: Int) -> Unit
) : RecyclerView.Adapter<DistributionAdapter.Holder>() {
    private var remainingMoney : Double = 0.0
    class Holder(val binding: ItemDistributionBinding) : ViewHolder(binding.root)

    fun setRemainingMoney(remainingMoney : Double){
        this.remainingMoney = remainingMoney
        notifyItemChanged(1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
        = Holder(ItemDistributionBinding.inflate(LayoutInflater.from(parent.context), parent,false))

    override fun getItemCount(): Int = list.size + 2 //el boton de añadir y el sobrante

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder.binding){
            root.setOnClickListener{
                if (position == 0){  onClickCreate() }
                else{
                    if (position == 1) return@setOnClickListener
                    onClick(list[position-2],position)
                }
            }
            root.setOnLongClickListener {
                if (position != 0 && position != 1){
                    onDelete(list[position-2],position)
                }
                return@setOnLongClickListener true
            }
            if(position == 0){
                imgAddDistribution.visibility = View.VISIBLE
                itemDistribution.visibility = View.GONE
            }else{
                imgAddDistribution.visibility = View.GONE
                itemDistribution.visibility = View.VISIBLE
                if(position == 1){
                    txtTitleItemDistribution.text = "Sobrante"
                    txtAmountItemDistribution.text = remainingMoney.formatSalary()
                    btnSettingsItemDistribution.visibility= View.GONE
                }else{
                    txtTitleItemDistribution.text = list[position -2].name
                    txtAmountItemDistribution.text = list[position -2].amountSaved.formatSalary()
                    imgItemDistribution.setImageResource(list[position -2].image)

                }
            }
        }
    }
}