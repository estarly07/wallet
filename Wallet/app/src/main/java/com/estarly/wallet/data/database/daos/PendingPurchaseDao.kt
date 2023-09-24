package com.estarly.wallet.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.estarly.wallet.data.database.entities.CDTEntity
import com.estarly.wallet.data.database.entities.DebtEntity
import com.estarly.wallet.data.database.entities.PendingPurchaseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PendingPurchaseDao {
    @Insert
    suspend fun insertPendingPurchase(pendingPurchaseEntity: PendingPurchaseEntity)
    @Query("SELECT * FROM pending_purchase_table")
    suspend fun getAllPendingPurchases() : List<PendingPurchaseEntity>?
    @Update
    suspend fun updatePendingPurchase(pendingPurchaseEntity: PendingPurchaseEntity)
    @Delete
    suspend fun deletePendingPurchase(pendingPurchaseEntity: PendingPurchaseEntity)
}