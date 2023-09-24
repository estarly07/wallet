package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.DebtModel
import com.estarly.wallet.domain.models.SalaryModel
import com.estarly.wallet.domain.repositories.DebtRepository
import com.estarly.wallet.domain.repositories.SalaryRepository
import javax.inject.Inject

class SaveDebtUseCase @Inject constructor(
    private val debtRepository: DebtRepository
) {
    suspend operator fun invoke(
        name : String, amountInitial : Double?, amountPaid : Double
    ) = debtRepository.insertDebt(
        DebtModel(
            0,
            amountInitial ?: 0.0,
            amountPaid,
            name,
            System.currentTimeMillis(),
            missingAmount = 0.0,
            finished = false
            )
        )
}