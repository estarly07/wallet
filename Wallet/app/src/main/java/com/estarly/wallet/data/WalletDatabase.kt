package com.estarly.wallet.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.estarly.wallet.data.database.daos.DebtDao
import com.estarly.wallet.data.database.daos.DistributionOfMoneyDao
import com.estarly.wallet.data.database.daos.SalaryDao
import com.estarly.wallet.data.database.daos.TransactionDao
import com.estarly.wallet.data.database.daos.CDTDao
import com.estarly.wallet.data.database.daos.GoalDao
import com.estarly.wallet.data.database.daos.PendingPurchaseDao
import com.estarly.wallet.data.database.entities.CDTEntity
import com.estarly.wallet.data.database.entities.DebtEntity
import com.estarly.wallet.data.database.entities.DistributionOfMoneyEntity
import com.estarly.wallet.data.database.entities.GoalEntity
import com.estarly.wallet.data.database.entities.PendingPurchaseEntity
import com.estarly.wallet.data.database.entities.SalaryEntity
import com.estarly.wallet.data.database.entities.TransactionsEntity

@Database(
    version = 2,
    entities = [
        DebtEntity::class,
        DistributionOfMoneyEntity::class,
        GoalEntity::class,
        SalaryEntity::class,
        TransactionsEntity::class,
        CDTEntity::class,
        PendingPurchaseEntity::class
    ],
)
abstract class WalletDatabase : RoomDatabase(){
    abstract fun getSalaryDao() : SalaryDao
    abstract fun getDistributionOfMoneyDao() : DistributionOfMoneyDao
    abstract fun getTransactionDao() : TransactionDao
    abstract fun getDebtDao() : DebtDao
    abstract fun getCDTDao() : CDTDao
    abstract fun getGoalDao() : GoalDao
    abstract fun getPendingPurchaseDao() : PendingPurchaseDao
    companion object{
        val MIGRATION_1_2 = object : Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS pending_purchase_table (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, amount DOUBLE NOT NULL, name TEXT NOT NULL, finished INTEGER NOT NULL, image TEXT NOT NULL)")
            }
        }
    }
}