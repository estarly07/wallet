package com.estarly.wallet.domain.models

data class PendingPurchaseModel(
    val id : Int,
    val amount : Double,
    var amountFormatted : String = "",
    val name : String,
    val finished : Boolean,
    val image : String,
)
