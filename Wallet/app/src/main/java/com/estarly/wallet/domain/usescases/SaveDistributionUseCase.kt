package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.SalaryModel
import com.estarly.wallet.domain.repositories.DistributionOfMoneyRepository
import com.estarly.wallet.domain.repositories.SalaryRepository
import javax.inject.Inject

class SaveDistributionUseCase @Inject constructor(
    private val distributionOfMoneyRepository: DistributionOfMoneyRepository
) {
    suspend operator fun invoke(distributionOfMoneyModel: DistributionOfMoneyModel){ distributionOfMoneyRepository.saveDistribution(distributionOfMoneyModel)}
}