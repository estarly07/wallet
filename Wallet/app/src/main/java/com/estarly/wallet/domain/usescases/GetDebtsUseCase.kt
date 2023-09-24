package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.DebtModel
import com.estarly.wallet.domain.repositories.DebtRepository
import com.estarly.wallet.domain.repositories.SalaryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDebtsUseCase @Inject constructor(
    private val debtRepository: DebtRepository
) {
     operator fun invoke(): Flow<List<DebtModel>?> = debtRepository.debts
}