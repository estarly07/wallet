package com.estarly.wallet.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.estarly.wallet.databinding.ItemGoalBinding
import com.estarly.wallet.domain.models.GoalModel
import com.estarly.wallet.utils.formatSalary

class GoalsAdapter(
    val list: List<GoalModel>,
    val onClick  : (GoalModel) -> Unit,
    val onDelete : (GoalModel) -> Unit,
) : RecyclerView.Adapter<GoalsAdapter.Holder>() {
    class Holder(val binding: ItemGoalBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder
        = Holder(ItemGoalBinding.inflate(LayoutInflater.from(parent.context), parent,false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder.binding){
            val goal = list[position]
            root.setOnClickListener { onClick(goal) }
            root.setOnLongClickListener {
                onDelete(goal)
                return@setOnLongClickListener true
            }
            txtNameGoal.text = goal.name
            txtDescriptionGoal.text = goal.description.ifEmpty { goal.amountGoal.formatSalary() }
            Glide.with(holder.itemView.context)
                .load(goal.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgItemGoal)
        }
    }
}