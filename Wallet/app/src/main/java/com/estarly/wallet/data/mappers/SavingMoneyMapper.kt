package com.estarly.wallet.data.mappers

import com.estarly.wallet.data.database.entities.SavinMoneyEntity
import com.estarly.wallet.domain.models.SavinMoneyModel
import com.estarly.wallet.utils.formatSalary
import com.estarly.wallet.utils.parseDate

fun SavinMoneyModel .parseEntity() = SavinMoneyEntity(id, amountSaving, /*System.currentTimeMillis()*/)
fun SavinMoneyEntity.parseModel () = SavinMoneyModel (id, amountSaving, amountSavingFormatted = amountSaving.formatSalary(), lastAmountDepositedFormatted = "",)
