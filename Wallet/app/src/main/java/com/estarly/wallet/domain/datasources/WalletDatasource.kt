package com.estarly.wallet.domain.datasources

import com.estarly.wallet.data.database.entities.CDTEntity
import com.estarly.wallet.data.database.entities.DebtEntity
import com.estarly.wallet.data.database.entities.DistributionOfMoneyEntity
import com.estarly.wallet.data.database.entities.GoalEntity
import com.estarly.wallet.data.database.entities.PendingPurchaseEntity
import com.estarly.wallet.data.database.entities.SalaryEntity
import com.estarly.wallet.data.database.entities.SavinMoneyEntity
import com.estarly.wallet.data.database.entities.TransactionsEntity
import kotlinx.coroutines.flow.Flow

interface WalletDatasource {
    val salary : Flow<SalaryEntity?>
    val distributions : Flow<List<DistributionOfMoneyEntity>?>
    val theLastThreeTransactions : Flow<List<TransactionsEntity>?>
    val debts : Flow<List<DebtEntity>?>
    val cdts : Flow<List<CDTEntity>?>
    val goals: Flow<List<GoalEntity>?>
    suspend fun saveSalary(salaryEntity: SalaryEntity)
    suspend fun updateSalary(salaryEntity: SalaryEntity)
    suspend fun getSalary() : SalaryEntity?
    suspend fun saveDistribution(distributionOfMoneyEntity: DistributionOfMoneyEntity)
    suspend fun getAllDistributions():List<DistributionOfMoneyEntity>?
    suspend fun deleteDistribution(distributionOfMoneyEntity:DistributionOfMoneyEntity)
    suspend fun updateDistribution(distributionOfMoneyEntity: DistributionOfMoneyEntity)
    suspend fun getDistribution(id: Int) : DistributionOfMoneyEntity
    suspend fun insertTransaction(transactionsEntity: TransactionsEntity)
    suspend fun getAllTransaction() : List<TransactionsEntity>?
    suspend fun insertDebt(debtEntity: DebtEntity)
    suspend fun getDebts(): List<DebtEntity>?
    suspend fun getDebtsDoNotFinished(): List<DebtEntity>?
    suspend fun updateDebt(debtEntity: DebtEntity)
    suspend fun insertCDT(cdtEntity: CDTEntity)
    suspend fun getCDTS():List<CDTEntity>?
    suspend fun updateCDT(cdtEntity: CDTEntity)
    suspend fun getCDT(id: Int) : CDTEntity?
    suspend fun insertGoal(goalEntity: GoalEntity)
    suspend fun updateGoal(parseEntity: GoalEntity)
    suspend fun insertPendingPurchase(pendingPurchaseEntity: PendingPurchaseEntity)
    suspend fun getPendingPurchases():List<PendingPurchaseEntity>?
    suspend fun updatePendingPurchases(pendingPurchaseEntity: PendingPurchaseEntity)
    suspend fun deletePendingPurchase(pendingPurchaseEntity: PendingPurchaseEntity)
    suspend fun updateSavingMoney(savinMoneyEntity: SavinMoneyEntity)
    suspend fun saveSavingMoney(savinMoneyEntity: SavinMoneyEntity)
    suspend fun getSavingMoney() : SavinMoneyEntity?
}