package com.estarly.wallet.domain.models

data class GoalModel(
    val id : Int,
    val name : String,
    val image : String,
    val description : String,
    val amountSaved : Double,
    val amountGoal : Double,
    val finished : Boolean
)
