package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.repositories.GoalRepository
import javax.inject.Inject

class DeleteGoalUseCase @Inject constructor(
    private val goalRepository: GoalRepository
) {
    suspend operator fun invoke(idGoal : Int) { goalRepository.getGoal(idGoal)?.let { goalRepository.deleteGoal(it)}}
}