package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.SalaryModel
import com.estarly.wallet.domain.models.TypeTransactions
import com.estarly.wallet.domain.repositories.SalaryRepository
import javax.inject.Inject

class TakeMoneyOutUseCase @Inject constructor(
    private val salaryRepository: SalaryRepository,
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val updateDistributionOfMoneyUseCase: UpdateDistributionOfMoneyUseCase
) {
    suspend operator fun invoke(
        amount: String,
        whyTakeOfMoney: String?,
        distribution: DistributionOfMoneyModel?
    ){
        if(amount.isEmpty()) { return }
        val salaryModel = salaryRepository.getSalary()
        salaryModel?.let {
            salaryModel.amount -= amount.toDouble()
            salaryRepository.updateSalary(salaryModel)
            if(distribution!=null){
                val newAmount = distribution.amountSaved - amount.toDouble()
                updateDistributionOfMoneyUseCase(distribution.copy(amountSaved = newAmount))
            }
            val description = if(distribution!=null) {
                if(whyTakeOfMoney!=null)
                    "Se retiró de ${distribution.name} para $whyTakeOfMoney"
                else
                    "Se retiró de ${distribution.name}"
            } else whyTakeOfMoney
            insertTransactionUseCase(
                amount,
                description,
                TypeTransactions.TAKE_MONEY_OUT
            )
        }
    }
}