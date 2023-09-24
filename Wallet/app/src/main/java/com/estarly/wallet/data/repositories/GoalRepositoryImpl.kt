package com.estarly.wallet.data.repositories

import com.estarly.wallet.data.mappers.parseEntity
import com.estarly.wallet.data.mappers.parseModel
import com.estarly.wallet.domain.datasources.WalletDatasource
import com.estarly.wallet.domain.models.CDTModel
import com.estarly.wallet.domain.models.GoalModel
import com.estarly.wallet.domain.repositories.CDTRepository
import com.estarly.wallet.domain.repositories.GoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GoalRepositoryImpl(
    private val walletDatasource: WalletDatasource
) : GoalRepository {
    override val goals: Flow<List<GoalModel>?> = walletDatasource.goals.map { it -> it?.map { it.parseModel() } }

    override suspend fun insertGoal(goalModel: GoalModel) = walletDatasource.insertGoal(goalModel.parseEntity())
    override suspend fun updateGoal(goalModel: GoalModel) = walletDatasource.updateGoal(goalModel.parseEntity())
}