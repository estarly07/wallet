package com.estarly.wallet.domain.repositories

import com.estarly.wallet.domain.models.GoalModel
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    val goals : Flow<List<GoalModel>?>
    suspend fun insertGoal(goalModel: GoalModel)
    suspend fun updateGoal(goalModel: GoalModel)
    suspend fun deleteGoal(goalModel: GoalModel)
    suspend fun getGoal(idGoal : Int) : GoalModel?
}