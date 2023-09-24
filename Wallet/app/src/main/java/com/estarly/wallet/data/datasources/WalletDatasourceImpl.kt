package com.estarly.wallet.data.datasources

import com.estarly.wallet.data.database.daos.CDTDao
import com.estarly.wallet.data.database.daos.DebtDao
import com.estarly.wallet.data.database.daos.DistributionOfMoneyDao
import com.estarly.wallet.data.database.daos.GoalDao
import com.estarly.wallet.data.database.daos.PendingPurchaseDao
import com.estarly.wallet.data.database.daos.SalaryDao
import com.estarly.wallet.data.database.daos.TransactionDao
import com.estarly.wallet.data.database.entities.CDTEntity
import com.estarly.wallet.data.database.entities.DebtEntity
import com.estarly.wallet.data.database.entities.DistributionOfMoneyEntity
import com.estarly.wallet.data.database.entities.GoalEntity
import com.estarly.wallet.data.database.entities.PendingPurchaseEntity
import com.estarly.wallet.data.database.entities.SalaryEntity
import com.estarly.wallet.data.database.entities.TransactionsEntity
import com.estarly.wallet.domain.datasources.WalletDatasource
import kotlinx.coroutines.flow.Flow

class WalletDatasourceImpl(
    private val salaryDao: SalaryDao,
    private val distributionOfMoneyDao: DistributionOfMoneyDao,
    private val transactionDao: TransactionDao,
    private val debtDao: DebtDao,
    private val cdtDao: CDTDao,
    private val goalDao: GoalDao,
    private val pendingPurchaseDao: PendingPurchaseDao,
) : WalletDatasource{
    override val salary: Flow<SalaryEntity?> = salaryDao.getSalary()
    override val distributions : Flow<List<DistributionOfMoneyEntity>?> = distributionOfMoneyDao.getDistributions()
    override val theLastThreeTransactions: Flow<List<TransactionsEntity>?> = transactionDao.selectTheLastThreeTransactions()
    override val debts: Flow<List<DebtEntity>?> = debtDao.getDebts()
    override val cdts: Flow<List<CDTEntity>?> = cdtDao.getCDTS()
    override val goals: Flow<List<GoalEntity>?> = goalDao.getGoals()

    override suspend fun saveSalary(salaryEntity: SalaryEntity)   = salaryDao.saveSalary(salaryEntity)
    override suspend fun updateSalary(salaryEntity: SalaryEntity) = salaryDao.updateSalary(salaryEntity)
    override suspend fun getSalary(): SalaryEntity? = salaryDao.getSalaryNoFlow()
    override suspend fun saveDistribution(distributionOfMoneyEntity: DistributionOfMoneyEntity) = distributionOfMoneyDao.saveDistribution(distributionOfMoneyEntity)
    override suspend fun getAllDistributions(): List<DistributionOfMoneyEntity>? = distributionOfMoneyDao.getAllDistributions()
    override suspend fun updateDistribution(distributionOfMoneyEntity: DistributionOfMoneyEntity) = distributionOfMoneyDao.updateDistribution(distributionOfMoneyEntity)
    override suspend fun getDistribution(id: Int): DistributionOfMoneyEntity = distributionOfMoneyDao.getDistribution(id)
    override suspend fun insertTransaction(transactionsEntity: TransactionsEntity) = transactionDao.insertTransaction(transactionsEntity)
    override suspend fun getAllTransaction(): List<TransactionsEntity>? = transactionDao.getAllTransactions()
    override suspend fun insertDebt(debtEntity: DebtEntity) = debtDao.saveDebt(debtEntity)
    override suspend fun getDebts(): List<DebtEntity>? = debtDao.getAllDebts()
    override suspend fun getDebtsDoNotFinished(): List<DebtEntity>? = debtDao.getDebtsDoNotFinished()
    override suspend fun updateDebt(debtEntity: DebtEntity) = debtDao.updateDebt(debtEntity)
    override suspend fun insertCDT(cdtEntity: CDTEntity) = cdtDao.insertCDT(cdtEntity)
    override suspend fun getCDTS(): List<CDTEntity>? =cdtDao.getAllCDTS()
    override suspend fun updateCDT(cdtEntity: CDTEntity) = cdtDao.updateCDT(cdtEntity)
    override suspend fun getCDT(id: Int): CDTEntity? = cdtDao.getCDT(id)
    override suspend fun insertGoal(goalEntity: GoalEntity) = goalDao.insertGoal(goalEntity)
    override suspend fun updateGoal(goalEntity: GoalEntity) = goalDao.updateGoal(goalEntity)
    override suspend fun insertPendingPurchase(pendingPurchaseEntity: PendingPurchaseEntity) = pendingPurchaseDao.insertPendingPurchase(pendingPurchaseEntity)
    override suspend fun getPendingPurchases(): List<PendingPurchaseEntity>? = pendingPurchaseDao.getAllPendingPurchases()
    override suspend fun updatePendingPurchases(pendingPurchaseEntity: PendingPurchaseEntity) = pendingPurchaseDao.updatePendingPurchase(pendingPurchaseEntity)
    override suspend fun deletePendingPurchase(pendingPurchaseEntity: PendingPurchaseEntity) = pendingPurchaseDao.deletePendingPurchase(pendingPurchaseEntity)
}