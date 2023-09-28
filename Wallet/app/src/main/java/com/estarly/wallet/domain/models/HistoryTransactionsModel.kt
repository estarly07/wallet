package com.estarly.wallet.domain.models

import com.estarly.wallet.utils.formatSalary

data class HistoryTransactionsModel(
    val date  : String,
    val month : Int,
    val year  : Int,
    var salary: Double = 0.0,
    var salaryFormatted: String = salary.formatSalary(),
    val percentageSpent    : PercentageModel = PercentageModel(name = "Porcentaje gastado"),
    val percentagePaid     : PercentageModel = PercentageModel(name = "Porcentaje pagado en deudas"),
    val percentageDeposits : PercentageModel = PercentageModel(name = "Porcentaje de transacciones de deposito"),
    val percentageSpents   : PercentageModel = PercentageModel(name = "Porcentaje de transacciones de retiro"),
    val percentagePaids    : PercentageModel = PercentageModel(name = "Porcentaje de transacciones de pagos"),
)
