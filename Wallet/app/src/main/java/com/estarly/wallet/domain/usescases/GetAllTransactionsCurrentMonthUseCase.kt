package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.TransactionModel
import com.estarly.wallet.domain.repositories.TransactionRepository
import com.estarly.wallet.utils.getMonth
import javax.inject.Inject

class GetAllTransactionsCurrentMonthUseCase @Inject constructor(
    private val  transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(): List<TransactionModel> {
        val currentMonth = System.currentTimeMillis().getMonth()
        val transactions = transactionRepository.getAllTransaction()
        val transactionsCurrentMonth = mutableListOf<TransactionModel>()
        for (transaction in transactions){
            if(transaction.date.getMonth() == currentMonth)
                transactionsCurrentMonth.add(transaction)
        }
        return transactionsCurrentMonth
    }
}