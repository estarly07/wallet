package com.estarly.wallet.data.mappers

import com.estarly.wallet.data.database.entities.TransactionsEntity
import com.estarly.wallet.domain.models.TransactionModel
import com.estarly.wallet.domain.models.TypeTransactions
import com.estarly.wallet.utils.parseDate

fun TransactionModel .parseEntity() = TransactionsEntity(id, description, amount, date, typeTransaction.toString())
fun TransactionsEntity.parseModel () = TransactionModel(
        id,
        description,
        amount,
        date,
        dateFormatted =date.parseDate(),
        TypeTransactions.enumValueOf(typeTransaction)
    )
