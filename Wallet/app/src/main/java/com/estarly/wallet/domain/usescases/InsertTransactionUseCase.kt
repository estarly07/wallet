package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.TransactionModel
import com.estarly.wallet.domain.models.TypeTransactions
import com.estarly.wallet.domain.repositories.TransactionRepository
import javax.inject.Inject

class InsertTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(amount: String, description: String?, typeTransactions: TypeTransactions,){ transactionRepository.insertTransaction(
        TransactionModel(
            0,
            description ?: typeTransactions.type,
            amount.toDouble(),
            System.currentTimeMillis(),
            typeTransaction = typeTransactions
        )
    ) }
}