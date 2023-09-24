package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.DebtModel
import com.estarly.wallet.domain.repositories.DebtRepository
import javax.inject.Inject

class GetAllDebtsDoNotFinishedUseCase @Inject constructor(
    private val  debtRepository: DebtRepository
) {
    suspend operator fun invoke(): List<DebtModel> = debtRepository.getDebtsDoNotFinished()
}