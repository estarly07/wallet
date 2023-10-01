package com.estarly.wallet.data.mappers

import com.estarly.wallet.data.database.entities.DistributionOfMoneyEntity
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.getColorBackground
import com.estarly.wallet.domain.models.getNameColorBackground

fun DistributionOfMoneyModel .parseEntity() = DistributionOfMoneyEntity(
    id,
    amountSaved,
    name,
    getNameColorBackground(image))
fun DistributionOfMoneyEntity.parseModel () = DistributionOfMoneyModel(
    id,
    amountSaved,
    name,
    getColorBackground(image))
