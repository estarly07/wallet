package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.SalaryModel
import com.estarly.wallet.domain.models.TypeTransactions
import com.estarly.wallet.domain.repositories.SalaryRepository
import javax.inject.Inject

class UpdateSalaryUseCase @Inject constructor(
    private val salaryRepository: SalaryRepository,
    private val saveSalaryUseCase  : SaveSalaryUseCase,
    private val updateDistributionOfMoneyUseCase: UpdateDistributionOfMoneyUseCase,
    private val insertTransactionUseCase: InsertTransactionUseCase
) {
    suspend operator fun invoke(
        amount: String,
        distribution: DistributionOfMoneyModel?
    ){
        val salaryModel = salaryRepository.getSalary()
        if(salaryModel != null) {
            salaryModel.amount += amount.toDouble()
            salaryRepository.updateSalary(salaryModel)
        }else {
            saveSalaryUseCase(SalaryModel(0, amount.toDouble()))
        }
        if(distribution!=null){
            val newAmount = distribution.amountSaved + amount.toDouble()
            updateDistributionOfMoneyUseCase(distribution.copy(amountSaved = newAmount))
        }
        insertTransactionUseCase(
            amount,
            if(distribution!=null) "Se deposita en ${distribution.name}" else null,
            TypeTransactions.DEPOSIT
        )
    }
}