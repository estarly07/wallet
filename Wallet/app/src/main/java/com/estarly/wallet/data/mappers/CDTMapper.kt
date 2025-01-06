package com.estarly.wallet.data.mappers

import com.estarly.wallet.data.database.entities.CDTEntity
import com.estarly.wallet.data.database.entities.DebtEntity
import com.estarly.wallet.domain.models.CDTModel
import com.estarly.wallet.domain.models.DebtModel
import com.estarly.wallet.domain.models.getColorBackground
import com.estarly.wallet.domain.models.getNameColorBackground
import com.estarly.wallet.utils.getProfit
import com.estarly.wallet.utils.parseDate
import kotlin.math.roundToInt

fun CDTModel .parseEntity() = CDTEntity(
    id,
    amount,
    dateLastPaid,
    getNameColorBackground(image),
    days      = days,
    tea       = tea,
    tna       = tna,
    rteFuente = rteFuente,
)
fun CDTEntity.parseModel () = CDTModel(
    id,
    amount,
    dateLastPaid,
    dateLastPaid.parseDate(),
    getColorBackground(image),
    tna       = tna,
    tea       = tea,
    days      = days,
    rteFuente = rteFuente,
    profit    = tea?.let { amount.getProfit(tea = tea,tna = tna!!, days = days!!, rteFuente = rteFuente) } ?: "-"
)
