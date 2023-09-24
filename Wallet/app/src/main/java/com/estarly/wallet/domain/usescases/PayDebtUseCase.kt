package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.DebtModel
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.SalaryModel
import com.estarly.wallet.domain.models.TypeTransactions
import com.estarly.wallet.domain.repositories.DebtRepository
import com.estarly.wallet.domain.repositories.SalaryRepository
import javax.inject.Inject

class PayDebtUseCase @Inject constructor(
    private val salaryRepository: SalaryRepository,
    private val debtRepository: DebtRepository,
    private val updateDistributionOfMoneyUseCase: UpdateDistributionOfMoneyUseCase,
    private val insertTransactionUseCase: InsertTransactionUseCase,
) {
    suspend operator fun invoke(
        amount: String,
        distribution: DistributionOfMoneyModel?,
        debt:DebtModel
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
            debtRepository.updateDebt(debt.copy(
                amount = debt.amount + amount.toDouble(),
                dateLastPaid = System.currentTimeMillis(),
                finished     = (debt.amount + amount.toDouble()) == debt.amount_paid
            ))
            insertTransactionUseCase(
                amount,
                if(distribution!=null)
                    "Se abonó la deuda ${debt.name} y salió de ${distribution.name}"
                else
                    "Se abonó la deuda ${debt.name} y salió del disponible",
                TypeTransactions.PAY_DEBT
            )
        }
    }
}