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
import com.estarly.wallet.data.database.daos.SavingMoneyDao
import com.estarly.wallet.data.database.entities.CDTEntity
import com.estarly.wallet.data.database.entities.DebtEntity
import com.estarly.wallet.data.database.entities.DistributionOfMoneyEntity
import com.estarly.wallet.data.database.entities.GoalEntity
import com.estarly.wallet.data.database.entities.PendingPurchaseEntity
import com.estarly.wallet.data.database.entities.SalaryEntity
import com.estarly.wallet.data.database.entities.SavinMoneyEntity
import com.estarly.wallet.data.database.entities.TransactionsEntity

@Database(
    version = 5,
    entities = [
        DebtEntity::class,
        DistributionOfMoneyEntity::class,
        GoalEntity::class,
        SalaryEntity::class,
        TransactionsEntity::class,
        CDTEntity::class,
        PendingPurchaseEntity::class,
        SavinMoneyEntity::class,
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
    abstract fun getSavingMoneyDao() : SavingMoneyDao
    companion object{
        val MIGRATION_3_4 = object : Migration(3, 4){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS saving_money_table (id INTEGER PRIMARY KEY NOT NULL, amount_saving DOUBLE NOT NULL, last_amount_deposited LONG NOT NULL)")
            }
        }
        val MIGRATION_4_5 = object : Migration(4, 5){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE saving_money_table")
                database.execSQL("CREATE TABLE IF NOT EXISTS saving_money_table (id INTEGER PRIMARY KEY NOT NULL, amount_saving DOUBLE NOT NULL)")
            }
        }

    }
}