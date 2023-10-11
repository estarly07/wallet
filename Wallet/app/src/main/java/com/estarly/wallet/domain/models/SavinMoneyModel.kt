package com.estarly.wallet.domain.models

data class SavinMoneyModel(
    val id : Int,
    val amountSaving : Double,
    val amountSavingFormatted : String ="",
    val lastAmountDepositedFormatted : String =""
)
