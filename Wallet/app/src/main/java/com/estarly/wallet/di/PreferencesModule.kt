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
import com.estarly.wallet.data.database.daos.TransactionDao
import com.estarly.wallet.data.datasources.WalletPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {
    @Provides
    @Singleton
    fun providePreference(@ApplicationContext context: Context ) : WalletPreferences
     = WalletPreferences(context)

}