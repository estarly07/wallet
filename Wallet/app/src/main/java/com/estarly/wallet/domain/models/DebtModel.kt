package com.estarly.wallet.domain.models

data class DebtModel(
    val id : Int,
    val amount : Double,
    val amount_paid: Double,
    val name : String,
    val dateLastPaid : Long,
    val dateLastPaidFormatted : String? = "",
    val missingAmount : Double,
    val percentagePaid : Int = 0,
    val finished : Boolean
)
