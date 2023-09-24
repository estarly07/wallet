package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.repositories.DistributionOfMoneyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDistributionUseCase @Inject constructor(
    private val  distributionOfMoneyRepository: DistributionOfMoneyRepository
) {
    operator suspend fun invoke(id: Int): DistributionOfMoneyModel = distributionOfMoneyRepository.getDistribution(id)
}