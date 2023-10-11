package com.estarly.wallet.di

import com.estarly.wallet.data.database.daos.CDTDao
import com.estarly.wallet.data.database.daos.DebtDao
import com.estarly.wallet.data.database.daos.DistributionOfMoneyDao
import com.estarly.wallet.data.database.daos.GoalDao
import com.estarly.wallet.data.database.daos.PendingPurchaseDao
import com.estarly.wallet.data.database.daos.SalaryDao
import com.estarly.wallet.data.database.daos.SavingMoneyDao
import com.estarly.wallet.data.database.daos.TransactionDao
import com.estarly.wallet.data.datasources.WalletDatasourceImpl
import com.estarly.wallet.domain.datasources.WalletDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatasourceModule {
    @Provides
    @Singleton
    fun provideWalletDatasource(
        salaryDao: SalaryDao,
        distributionOfMoneyDao: DistributionOfMoneyDao,
        transactionDao: TransactionDao,
        debtDao: DebtDao,
        cdtDao: CDTDao,
        goalDao: GoalDao,
        pendingPurchaseDao: PendingPurchaseDao,
        savingMoneyDao: SavingMoneyDao
    ) : WalletDatasource
            = WalletDatasourceImpl(salaryDao,distributionOfMoneyDao,transactionDao,debtDao,cdtDao,goalDao,pendingPurchaseDao,savingMoneyDao)
}