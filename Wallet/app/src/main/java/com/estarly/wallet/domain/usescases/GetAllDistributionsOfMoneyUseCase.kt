package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.repositories.DistributionOfMoneyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllDistributionsOfMoneyUseCase @Inject constructor(
    private val  distributionOfMoneyRepository: DistributionOfMoneyRepository
) {
    suspend operator fun invoke(): List<DistributionOfMoneyModel> = distributionOfMoneyRepository.getAllDistributions() ?: listOf()
}