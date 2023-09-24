package com.estarly.wallet.domain.models


data class TransactionModel(
    val id              : Int ,
    val description     : String,
    val amount          : Double,
    val date            : Long,
    val dateFormatted   : String = "",
    val typeTransaction : TypeTransactions,
)
