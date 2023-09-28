package com.estarly.wallet.domain.usescases

import android.util.Log
import com.estarly.wallet.domain.models.HistoryTransactionsModel
import com.estarly.wallet.domain.models.TypeTransactions
import com.estarly.wallet.domain.repositories.TransactionRepository
import com.estarly.wallet.utils.formatSalary
import com.estarly.wallet.utils.getMonth
import com.estarly.wallet.utils.getPercentage
import com.estarly.wallet.utils.getYear
import com.estarly.wallet.utils.parseDateWithoutDay
import com.estarly.wallet.utils.plusElements
import javax.inject.Inject



class GetHistoryUseCase @Inject constructor(
    private val  transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(): List<HistoryTransactionsModel> {
        val records = mutableListOf<HistoryTransactionsModel>()
        val transactions = transactionRepository.getAllTransaction()

        for (transaction in transactions){
            val record = records.find { it.month == transaction.date.getMonth() && it.year == transaction.date.getYear() }
            if(record == null) {
                val transactionsByMonth = transactions.filter { it.date.getMonth() == transaction.date.getMonth() }
                val history = HistoryTransactionsModel(
                    date  = if(System.currentTimeMillis().getMonth() == transaction.date.getMonth()){
                        "Este mes"
                    }else if((System.currentTimeMillis().getMonth()- transaction.date.getMonth()) == 1){
                        "Mes pasado"
                    }else{
                        transaction.date.parseDateWithoutDay()
                    },
                    month = transaction.date.getMonth(),
                    year  = transaction.date.getYear(),
                    salary = transactionsByMonth.filter { it.typeTransaction == TypeTransactions.DEPOSIT }.map { it.amount }.plusElements(),
                )
                history.salaryFormatted = history.salary.formatSalary()
                history.percentageSpent.percentage    = transactionsByMonth.filter { it.typeTransaction == TypeTransactions.TAKE_MONEY_OUT }.map { it.amount }.plusElements().getPercentage(history.salary)
                history.percentagePaid.percentage     = transactionsByMonth.filter { it.typeTransaction == TypeTransactions.PAY_DEBT }.map { it.amount }.plusElements().getPercentage(history.salary)
                history.percentageDeposits.percentage = transactionsByMonth.filter { it.typeTransaction == TypeTransactions.DEPOSIT }.size.toDouble().getPercentage(transactionsByMonth.size.toDouble())
                history.percentageSpents.percentage   = transactionsByMonth.filter { it.typeTransaction == TypeTransactions.TAKE_MONEY_OUT }.size.toDouble().getPercentage(transactionsByMonth.size.toDouble())
                history.percentagePaids.percentage    = transactionsByMonth.filter { it.typeTransaction == TypeTransactions.PAY_DEBT }.size.toDouble().getPercentage(transactionsByMonth.size.toDouble())
                Log.i("GetHistoryUseCase","$history")
                records.add(history)
            }

        }
        return  records
    }
}