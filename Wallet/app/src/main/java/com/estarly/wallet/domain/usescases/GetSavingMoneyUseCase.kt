package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.SavinMoneyModel
import com.estarly.wallet.domain.repositories.SavingMoneyRepository
import javax.inject.Inject

class GetSavingMoneyUseCase @Inject constructor(
    private val savingMoneyRepository: SavingMoneyRepository
) {
    suspend operator fun invoke(): SavinMoneyModel? = savingMoneyRepository.getMoney()
}