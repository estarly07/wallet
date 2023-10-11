package com.estarly.wallet.di

import android.content.Context
import androidx.room.Room
import com.estarly.wallet.data.WalletDatabase
import com.estarly.wallet.data.database.daos.CDTDao
import com.estarly.wallet.data.database.daos.DebtDao
import com.estarly.wallet.data.database.daos.DistributionOfMoneyDao
import com.estarly.wallet.data.database.daos.GoalDao
import com.estarly.wallet.data.database.daos.PendingPurchaseDao
import com.estarly.wallet.data.database.daos.SalaryDao
import com.estarly.wallet.data.database.daos.SavingMoneyDao
import com.estarly.wallet.data.database.daos.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context ) : WalletDatabase
     = Room
        .databaseBuilder(context,WalletDatabase ::class.java,"wallet_database")
        .addMigrations(WalletDatabase.MIGRATION_3_4)
        .addMigrations(WalletDatabase.MIGRATION_4_5)
        .build()

    @Provides
    @Singleton
    fun provideSalaryDao(walletDatabase: WalletDatabase) : SalaryDao = walletDatabase.getSalaryDao()
    @Provides
    @Singleton
    fun provideDistributionOfMoneyDao(walletDatabase: WalletDatabase) : DistributionOfMoneyDao = walletDatabase.getDistributionOfMoneyDao()
    @Provides
    @Singleton
    fun provideTransactionDao(walletDatabase: WalletDatabase) : TransactionDao = walletDatabase.getTransactionDao()
    @Provides
    @Singleton
    fun provideDebtDao(walletDatabase: WalletDatabase) : DebtDao = walletDatabase.getDebtDao()
    @Provides
    @Singleton
    fun provideCDTDao(walletDatabase: WalletDatabase) : CDTDao = walletDatabase.getCDTDao()
    @Provides
    @Singleton
    fun provideGoalDao(walletDatabase: WalletDatabase) : GoalDao = walletDatabase.getGoalDao()
    @Provides
    @Singleton
    fun providePendingPurchaseDao(walletDatabase: WalletDatabase) : PendingPurchaseDao = walletDatabase.getPendingPurchaseDao()
    @Provides
    @Singleton
    fun provideSavingMoneyDao(walletDatabase: WalletDatabase) : SavingMoneyDao = walletDatabase.getSavingMoneyDao()
}