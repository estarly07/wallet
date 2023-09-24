package com.estarly.wallet.data.mappers

import com.estarly.wallet.data.database.entities.DebtEntity
import com.estarly.wallet.domain.models.DebtModel
import com.estarly.wallet.utils.parseDate
import kotlin.math.roundToInt

fun DebtModel .parseEntity() = DebtEntity(id, amount, amount_paid, name, dateLastPaid, if(finished) 1 else 0)
fun DebtEntity.parseModel () = DebtModel(
    id,
    amount,
    amount_paid,
    name,
    dateLastPaid,
    finished = finished == 1,
    dateLastPaidFormatted = dateLastPaid.parseDate(),
    missingAmount = amount_paid-amount,
    percentagePaid = (((amount * 100)/amount_paid).roundToInt())
)
