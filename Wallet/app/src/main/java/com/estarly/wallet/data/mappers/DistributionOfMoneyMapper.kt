package com.estarly.wallet.data.mappers

import com.estarly.wallet.R
import com.estarly.wallet.data.database.entities.DistributionOfMoneyEntity
import com.estarly.wallet.domain.models.DistributionOfMoneyModel

fun DistributionOfMoneyModel .parseEntity() = DistributionOfMoneyEntity(id, amountSaved, name, image.toString())
fun DistributionOfMoneyEntity.parseModel () = DistributionOfMoneyModel(id, amountSaved, name, image.toInt())
