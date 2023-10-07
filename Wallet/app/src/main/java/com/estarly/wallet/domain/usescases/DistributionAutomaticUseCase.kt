package com.estarly.wallet.domain.usescases

import android.util.Log
import com.estarly.wallet.domain.repositories.DistributionOfMoneyRepository
import com.estarly.wallet.utils.formatSalary
import javax.inject.Inject

class DistributionAutomaticUseCase @Inject constructor(
    private val distributionOfMoneyRepository: DistributionOfMoneyRepository,
    private val getAllDistributionsOfMoneyUseCase: GetAllDistributionsOfMoneyUseCase,
    private val getRemainingMoneyUseCase: GetRemainingMoneyUseCase,
    private val getAmountSalaryUseCase: GetAmountSalaryUseCase
) {
     suspend operator fun invoke(){
         val distributions = getAllDistributionsOfMoneyUseCase()
         for (distribution in distributions){
            val remainingSalary = getRemainingMoneyUseCase(getAmountSalaryUseCase())
             Log.i("DistributionAutomaticUseCase","disponible: ${remainingSalary.formatSalary()}")
            if(distribution.amountExpected > remainingSalary) return
             val newDistribution = distribution.copy(amountSaved = distribution.amountSaved + distribution.amountExpected)
             Log.i("DistributionAutomaticUseCase",newDistribution.amountSaved.formatSalary())
            distributionOfMoneyRepository.updateDistribution(newDistribution)
         }
     }
}