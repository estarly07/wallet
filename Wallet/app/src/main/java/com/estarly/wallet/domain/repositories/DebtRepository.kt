package com.estarly.wallet.domain.repositories

import com.estarly.wallet.domain.models.DebtModel
import kotlinx.coroutines.flow.Flow

interface DebtRepository {
    val debts : Flow<List<DebtModel>?>
    suspend fun insertDebt(debtModel: DebtModel)
    suspend fun getDebts():List<DebtModel>
    suspend fun getDebt(idDebt : Int):DebtModel
    suspend fun updateDebt(debtModel: DebtModel)
    suspend fun deleteDebt(debtModel: DebtModel)
    suspend fun getDebtsDoNotFinished(): List<DebtModel>
}