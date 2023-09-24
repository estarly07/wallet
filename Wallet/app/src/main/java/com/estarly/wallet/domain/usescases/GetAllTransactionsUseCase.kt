package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.TransactionModel
import com.estarly.wallet.domain.repositories.DistributionOfMoneyRepository
import com.estarly.wallet.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTransactionsUseCase @Inject constructor(
    private val  transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(): List<TransactionModel> = transactionRepository.getAllTransaction()
}