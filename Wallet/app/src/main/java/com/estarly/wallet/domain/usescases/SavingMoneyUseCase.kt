package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.SavinMoneyModel
import com.estarly.wallet.domain.models.TypeTransactions
import com.estarly.wallet.domain.repositories.SalaryRepository
import com.estarly.wallet.domain.repositories.SavingMoneyRepository
import javax.inject.Inject

class SavingMoneyUseCase @Inject constructor(
    private val salaryRepository: SalaryRepository,
    private val savingMoneyRepository: SavingMoneyRepository
) {
    suspend operator fun invoke(
        amount : Double
    ) {
        val salaryModel = salaryRepository.getSalary()
        salaryModel?.let {
            salaryModel.amount -= amount
            salaryRepository.updateSalary(salaryModel)
            val savingMoney = savingMoneyRepository.getMoney()
            if (savingMoney!=null){
                savingMoneyRepository.updateMoney(savingMoney.copy(amountSaving =  amount + savingMoney.amountSaving))
            }else{
                savingMoneyRepository.saveMoney(SavinMoneyModel(0,amount,))
            }
        }
    }
}