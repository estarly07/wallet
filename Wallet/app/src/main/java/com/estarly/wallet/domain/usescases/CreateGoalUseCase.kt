package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.GoalModel
import com.estarly.wallet.domain.repositories.GoalRepository
import javax.inject.Inject

class CreateGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {
    suspend operator fun invoke(
        name: String,
        description: String = "",
        amountInitial: Double,
        amountTotal: Double,
        image: String
    ){
        goalRepository.insertGoal(GoalModel(
            id = 0,
            name = name,
            description = description,
            amountSaved = amountInitial,
            amountGoal = amountTotal,
            image = image,
            finished = false
        ))
    }
}