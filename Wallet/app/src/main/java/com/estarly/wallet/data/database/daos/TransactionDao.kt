package com.estarly.wallet.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.estarly.wallet.data.database.entities.TransactionsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertTransaction(transactionsEntity: TransactionsEntity)
    @Query("SELECT * FROM transactions_table  ORDER BY id DESC LIMIT 3")
    fun selectTheLastThreeTransactions() : Flow<List<TransactionsEntity>>
    @Query("SELECT * FROM transactions_table  ORDER BY id DESC")
    suspend fun getAllTransactions() : List<TransactionsEntity>?
}