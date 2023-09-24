package com.estarly.wallet.data.mappers

import com.estarly.wallet.data.database.entities.CDTEntity
import com.estarly.wallet.data.database.entities.DebtEntity
import com.estarly.wallet.domain.models.CDTModel
import com.estarly.wallet.domain.models.DebtModel
import com.estarly.wallet.utils.parseDate
import kotlin.math.roundToInt

fun CDTModel .parseEntity() = CDTEntity(
    id,
    amount,
    dateLastPaid,
    image.toString()
)
fun CDTEntity.parseModel () = CDTModel(
    id,
    amount,
    dateLastPaid,
    dateLastPaid.parseDate(),
    image.toInt()
)
