package com.estarly.wallet.data.mappers

import com.estarly.wallet.data.database.entities.PendingPurchaseEntity
import com.estarly.wallet.domain.models.PendingPurchaseModel
import com.estarly.wallet.utils.formatSalary

fun PendingPurchaseModel .parseEntity() = PendingPurchaseEntity(
        id,
        amount,
        name,
        if(finished) 1 else 0,
        image
)
fun PendingPurchaseEntity.parseModel () = PendingPurchaseModel(
        id,
        amount,
        amount.formatSalary(),
        name,
        finished == 1,
        image
)
