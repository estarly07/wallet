package com.estarly.wallet.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.estarly.wallet.R
import com.estarly.wallet.databinding.ItemRecordBinding
import com.estarly.wallet.domain.models.HistoryTransactionsModel

class RecordAdapter(val list : List<HistoryTransactionsModel>) : RecyclerView.Adapter<RecordAdapter.Holder>() {
    class Holder(val binding: ItemRecordBinding) : ViewHolder(binding.root){
        var isExpanded: Boolean = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
        = Holder(ItemRecordBinding.inflate(LayoutInflater.from(parent.context), parent,false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder.binding){
            val history = list[position]
            txtDateHistoryItem.text = history.date
            txtSalaryHistoryItem.text = history.salaryFormatted
            recyclerAllProgress.layoutManager   = LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL,false)
            recyclerThreeProgress.layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL,false)
            val listProgress = listOf(
                history.percentageSpent,
                history.percentagePaid,
                history.percentageDeposits,
                history.percentageSpents,
                history.percentagePaids,
            )
            recyclerThreeProgress.adapter   = ProgressIndicatorAdapter(listProgress.subList(0,2))
            recyclerAllProgress.adapter = ProgressIndicatorAdapter(listProgress.subList(2,listProgress.size))

            recyclerAllProgress.isNestedScrollingEnabled   = false
            recyclerThreeProgress.isNestedScrollingEnabled = false
            btnShowMoreProgressIndicator.text = if (holder.isExpanded) "Mostrar menos" else "Mostrar más"

            btnShowMoreProgressIndicator.setOnClickListener {
                holder.isExpanded = !holder.isExpanded
                btnShowMoreProgressIndicator.text = if (holder.isExpanded) "Mostrar menos" else "Mostrar más"
                if (holder.isExpanded) {
                    expandRecyclerView(root.context,recyclerAllProgress, root)
                } else {
                    collapseRecyclerView(root.context,recyclerAllProgress)
                }
            }
        }
    }
    private fun expandRecyclerView(context: Context,recyclerView: RecyclerView, view: View) {
        val expandAnim = AnimationUtils.loadAnimation(context, R.anim.expand_collapse)
        view.startAnimation(expandAnim)

        val slideDown: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        recyclerView.startAnimation(slideDown)
        recyclerView.visibility = View.VISIBLE
    }

    private fun collapseRecyclerView(context: Context,recyclerView: RecyclerView, ) {
        val slideUp: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
        recyclerView.startAnimation(slideUp)
        recyclerView.visibility = View.GONE
    }
}