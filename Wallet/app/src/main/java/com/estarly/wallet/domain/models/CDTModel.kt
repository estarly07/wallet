package com.estarly.wallet.domain.models

data class CDTModel(
    val id           : Int,
    val amount       : Double,
    val dateLastPaid : Long,
    val image        : Int,
    val tea          : Double ? = null,
    val tna          : Double ? = null,
    val days         : Int ? = null,
    val rteFuente    : Double?,
    val profit       : String = "",
    var dateLastPaidFormatted : String = "",
)
