package com.estarly.wallet.domain.repositories

import com.estarly.wallet.domain.models.TransactionModel
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    val theLastThreeTransactions : Flow<List<TransactionModel>?>
    suspend fun insertTransaction(transactionsModel: TransactionModel)
    suspend fun getAllTransaction() : List<TransactionModel>
}