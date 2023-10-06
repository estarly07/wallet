package com.estarly.wallet.domain.usescases

import android.util.Log
import com.estarly.wallet.domain.models.TypeTransactions
import javax.inject.Inject

class GetPercentageMoneySpentCurrentMonthUseCase @Inject constructor(
    private val  getAllTransactionsCurrentMonthUseCase: GetAllTransactionsCurrentMonthUseCase,
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase
) {
    suspend operator fun invoke(): Double {
        val transactions = getAllTransactionsUseCase()
        var amountSpent = 0.0
        var amountDeposit = 0.0
        for ( transaction in transactions){
            if(transaction.typeTransaction != TypeTransactions.DEPOSIT)
                amountSpent += transaction.amount
            else
                amountDeposit += transaction.amount
        }
        Log.i("getPercentageSent","$amountDeposit $amountSpent ${((amountSpent/amountDeposit)*100)}")
        return (amountSpent/amountDeposit)*100
    }
}