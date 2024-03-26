package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.repositories.DebtRepository
import javax.inject.Inject

class DeleteDebtUseCase @Inject constructor(
    private val debtRepository: DebtRepository
) {
     suspend operator fun invoke(idDebt : Int) { debtRepository.deleteDebt(debtModel = debtRepository.getDebt(idDebt))}
}