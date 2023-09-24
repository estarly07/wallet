package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.CDTModel
import com.estarly.wallet.domain.models.DebtModel
import com.estarly.wallet.domain.models.GoalModel
import com.estarly.wallet.domain.repositories.CDTRepository
import com.estarly.wallet.domain.repositories.DebtRepository
import com.estarly.wallet.domain.repositories.GoalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllGoalsUseCase @Inject constructor(
    private val  goalRepository: GoalRepository
) {
    suspend operator fun invoke(): Flow<List<GoalModel>?> = goalRepository.goals
}