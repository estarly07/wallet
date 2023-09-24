package com.estarly.wallet.domain.usescases

import javax.inject.Inject

class GetRemainingMoneyUseCase @Inject constructor(
    private val getAllDistributionsOfMoneyUseCase: GetAllDistributionsOfMoneyUseCase,
) {
    suspend operator fun invoke (salaryTotal : Double) : Double{
        var amountAllDistribution = 0.0
        getAllDistributionsOfMoneyUseCase().forEach {
            amountAllDistribution += it.amountSaved
        }
        return salaryTotal - amountAllDistribution
    }
}