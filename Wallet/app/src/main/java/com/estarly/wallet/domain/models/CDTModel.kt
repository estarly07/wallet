package com.estarly.wallet.domain.models

data class CDTModel(
    val id : Int,
    val amount : Double,
    val dateLastPaid : Long,
    var dateLastPaidFormatted : String = "",
    val image : Int,
)
