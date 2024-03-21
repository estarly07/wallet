package com.estarly.wallet.domain.repositories

import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.SalaryModel
import kotlinx.coroutines.flow.Flow

interface DistributionOfMoneyRepository {
    val distributions : Flow<List<DistributionOfMoneyModel>?>
    suspend fun saveDistribution(distributionOfMoneyModel: DistributionOfMoneyModel)
    suspend fun getAllDistributions() : List<DistributionOfMoneyModel>?
    suspend fun updateDistribution(distributionOfMoneyModel: DistributionOfMoneyModel)
    suspend fun getDistribution(id: Int) : DistributionOfMoneyModel
    suspend fun deleteDistribution(distributionOfMoneyModel: DistributionOfMoneyModel)
}