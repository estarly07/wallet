package com.estarly.wallet.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.estarly.wallet.R
import com.estarly.wallet.databinding.ItemCedetesBinding
import com.estarly.wallet.domain.models.CDTModel
import com.estarly.wallet.utils.formatSalary

class CDTAdapter(
    private var total    : String           = "",
    private var list     : List<CDTModel>   = listOf(),
    private var onEdit   : (CDTModel)->Unit = {},
    private var onCreate : ()->Unit         = {}
) : RecyclerView.Adapter<CDTAdapter.Holder>() {
    class Holder(val binding: ItemCedetesBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
        = Holder(ItemCedetesBinding.inflate(LayoutInflater.from(parent.context), parent,false))

    override fun getItemCount(): Int = list.size + 1

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder.binding){
            total.root.isVisible = position == 0
            card.isVisible       = position != 0
            if(position == 0){
                total.txtTotalCdt.text = this@CDTAdapter.total
                total.btnCreateCDT.icon.setImageResource(R.drawable.ic_add)
                total.btnCreateCDT.root.setOnClickListener{onCreate()}
            }else{
                val cdt = list[position-1]
                imgBackgroundItemCedetes.setImageResource(cdt.image)
                txtDateCDT.text   = cdt.dateLastPaidFormatted
                txtAmountCDT.text = "$ ${cdt.amount.formatSalary()}"
                txtProfitCDT.text = "$ ${ cdt.profit }"
                btnEditCDT.setOnClickListener { onEdit(cdt) }
            }
        }
    }
    fun setTotal   (total: String)            {
        this@CDTAdapter.total = total
        notifyItemChanged(0)
    }
    fun setList    (list: List<CDTModel>)     { this@CDTAdapter.list  = list}
    fun setOnEdit  (onEdit: (CDTModel)->Unit) { this.onEdit = onEdit}
    fun setOnCreate(onCreate: ()->Unit)       { this.onCreate = onCreate}
}