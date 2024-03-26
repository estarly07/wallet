package com.estarly.wallet.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.estarly.wallet.data.database.entities.DebtEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DebtDao {
    @Insert
    suspend fun saveDebt(debtEntity: DebtEntity)
    @Query("SELECT * FROM debt_table")
    fun getDebts() : Flow<List<DebtEntity>?>
    @Query("SELECT * FROM debt_table")
    suspend fun getAllDebts() : List<DebtEntity>?
    @Query("SELECT * FROM debt_table WHERE finished = 0")
    suspend fun getDebtsDoNotFinished() : List<DebtEntity>?
    @Update
    suspend fun updateDebt(debtEntity: DebtEntity)
    @Delete
    suspend fun deleteDebt(debtEntity: DebtEntity)
    @Query("SELECT * FROM debt_table WHERE id=:idDebt")
    suspend fun getDebt(idDebt : Int): DebtEntity
}