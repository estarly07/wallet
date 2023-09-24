package com.estarly.wallet.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.estarly.wallet.data.database.entities.CDTEntity
import com.estarly.wallet.data.database.entities.DebtEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CDTDao {
    @Insert
    suspend fun insertCDT(cdtEntity: CDTEntity)
    @Query("SELECT * FROM cdt_table")
    fun getCDTS() : Flow<List<CDTEntity>?>
    @Query("SELECT * FROM cdt_table")
    suspend fun getAllCDTS() : List<CDTEntity>?
    @Query("SELECT * FROM cdt_table WHERE id=:id")
    suspend fun getCDT(id: Int) : CDTEntity?
    @Update
    suspend fun updateCDT(cdtEntity: CDTEntity)
}