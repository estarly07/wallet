package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.repositories.DistributionOfMoneyRepository
import javax.inject.Inject

class DeleteDistributionOfMoneyUseCase @Inject constructor(
    private val  distributionOfMoneyRepository: DistributionOfMoneyRepository,
) {
    suspend operator fun invoke(id:Int) {distributionOfMoneyRepository.deleteDistribution(distributionOfMoneyRepository.getDistribution(id))}
}