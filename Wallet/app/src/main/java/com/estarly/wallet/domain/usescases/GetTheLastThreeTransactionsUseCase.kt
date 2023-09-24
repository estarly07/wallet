package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.TransactionModel
import com.estarly.wallet.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTheLastThreeTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke(): Flow<List<TransactionModel>?> = transactionRepository.theLastThreeTransactions
}