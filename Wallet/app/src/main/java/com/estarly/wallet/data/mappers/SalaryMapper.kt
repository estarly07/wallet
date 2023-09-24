package com.estarly.wallet.data.mappers

import com.estarly.wallet.data.database.entities.SalaryEntity
import com.estarly.wallet.domain.models.SalaryModel

fun SalaryModel .parseEntity() = SalaryEntity(id, amount)
fun SalaryEntity.parseModel () = SalaryModel (id, amount)
