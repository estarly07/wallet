package com.estarly.wallet.presentation.adapters

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.estarly.wallet.R
import com.estarly.wallet.databinding.ItemPendingPurchaseBinding
import com.estarly.wallet.domain.models.PendingPurchaseModel


class PendingPurchaseAdapter(
    private val list: List<PendingPurchaseModel>,
    private val onUpdate : (PendingPurchaseModel) -> Unit,
    private val onFinishedPurchase : (PendingPurchaseModel, Boolean) -> Unit
) : RecyclerView.Adapter<PendingPurchaseAdapter.Holder>() {
    class Holder(val binding: ItemPendingPurchaseBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
        = Holder(ItemPendingPurchaseBinding.inflate(LayoutInflater.from(parent.context), parent,false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder.binding){
            val pendingPurchase = list[position]
            txtAmountPurchase.text = pendingPurchase.amountFormatted
            txtNamePurchase.text   = pendingPurchase.name
            btnEditPendingPurchase.setOnClickListener {
                showPopupMenu(holder.itemView.context,btnEditPendingPurchase, pendingPurchase)
            }
            Glide.with(holder.itemView.context)
                .load(pendingPurchase.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgItemPurchase)

        }
    }

    private fun showPopupMenu(context : Context,view:View, pendingPurchase: PendingPurchaseModel) {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = layoutInflater.inflate(R.layout.popup_menu_pending_purchase, null)
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val switch = popupView.findViewById<Switch>(R.id.swtEditPendingPurchase)
        switch.isChecked = pendingPurchase.finished
        switch.setOnCheckedChangeListener { _, isChecked ->
            onFinishedPurchase(pendingPurchase,isChecked)
        }
        popupView.findViewById<TextView>(R.id.txtEditPendingPurchase).setOnClickListener {
            onUpdate(pendingPurchase)
            popupWindow.dismiss()
        }

        popupWindow.setBackgroundDrawable(BitmapDrawable())
        popupWindow.isOutsideTouchable = true
        popupWindow.setOnDismissListener {
            //TODO do sth here on dismiss
        }

        popupWindow.showAsDropDown(view)
    }
}