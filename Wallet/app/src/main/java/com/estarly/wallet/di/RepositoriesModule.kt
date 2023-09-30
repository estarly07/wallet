package com.estarly.wallet.di

import com.estarly.wallet.data.datasources.WalletPreferences
import com.estarly.wallet.data.repositories.CDTRepositoryImpl
import com.estarly.wallet.data.repositories.DebtRepositoryImpl
import com.estarly.wallet.data.repositories.DistributionOfMoneyRepositoryImpl
import com.estarly.wallet.data.repositories.GoalRepositoryImpl
import com.estarly.wallet.data.repositories.PendingPurchaseRepositoryImpl
import com.estarly.wallet.data.repositories.SalaryRepositoryImpl
import com.estarly.wallet.data.repositories.TransactionRepositoryImpl
import com.estarly.wallet.data.repositories.UserRepositoryImpl
import com.estarly.wallet.domain.datasources.WalletDatasource
import com.estarly.wallet.domain.repositories.CDTRepository
import com.estarly.wallet.domain.repositories.DebtRepository
import com.estarly.wallet.domain.repositories.DistributionOfMoneyRepository
import com.estarly.wallet.domain.repositories.GoalRepository
import com.estarly.wallet.domain.repositories.PendingPurchaseRepository
import com.estarly.wallet.domain.repositories.SalaryRepository
import com.estarly.wallet.domain.repositories.TransactionRepository
import com.estarly.wallet.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {
    @Provides
    @Singleton
    fun provideSalaryRepository(walletDatasource: WalletDatasource) : SalaryRepository
        = SalaryRepositoryImpl(walletDatasource)
    @Provides
    @Singleton
    fun provideDistributionOfMoneyRepository(walletDatasource: WalletDatasource) : DistributionOfMoneyRepository
            = DistributionOfMoneyRepositoryImpl(walletDatasource)
    @Provides
    @Singleton
    fun provideTransactionRepository(walletDatasource: WalletDatasource) : TransactionRepository
            = TransactionRepositoryImpl(walletDatasource)
    @Provides
    @Singleton
    fun provideDebtRepository(walletDatasource: WalletDatasource) : DebtRepository
            = DebtRepositoryImpl(walletDatasource)
    @Provides
    @Singleton
    fun provideCDTRepository(walletDatasource: WalletDatasource) : CDTRepository
            = CDTRepositoryImpl(walletDatasource)
    @Provides
    @Singleton
    fun provideGoalRepository(walletDatasource: WalletDatasource) : GoalRepository
            = GoalRepositoryImpl(walletDatasource)
    @Provides
    @Singleton
    fun providePendingPurchaseRepository(walletDatasource: WalletDatasource) : PendingPurchaseRepository
            = PendingPurchaseRepositoryImpl(walletDatasource)
    @Provides
    @Singleton
    fun provideUserRepository(walletPreferences: WalletPreferences) : UserRepository
            = UserRepositoryImpl(walletPreferences)
}