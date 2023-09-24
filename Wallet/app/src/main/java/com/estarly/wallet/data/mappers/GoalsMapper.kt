package com.estarly.wallet.data.mappers

import com.estarly.wallet.data.database.entities.GoalEntity
import com.estarly.wallet.domain.models.GoalModel

fun GoalModel .parseEntity() = GoalEntity(
   id, name, image, description, amountSaved, amountGoal, if(finished) 1 else 0
)
fun GoalEntity.parseModel () = GoalModel(
    id,
    name,
    image,
    description,
    amountSaved,
    amountGoal,
    finished == 1
)
