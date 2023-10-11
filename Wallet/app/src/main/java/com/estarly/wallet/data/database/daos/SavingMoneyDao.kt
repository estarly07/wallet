package com.estarly.wallet.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.estarly.wallet.data.database.entities.SalaryEntity
import com.estarly.wallet.data.database.entities.SavinMoneyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavingMoneyDao {
    @Update
    suspend fun updateSavingMoney(savinMoneyEntity: SavinMoneyEntity)
    @Insert
    suspend fun saveSavingMoney(savinMoneyEntity: SavinMoneyEntity)
    @Query("SELECT * FROM saving_money_table LIMIT 1")
    suspend fun getSavingMoney() : SavinMoneyEntity?
}