package com.estarly.wallet.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.estarly.wallet.data.database.entities.DistributionOfMoneyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DistributionOfMoneyDao {
    @Insert
    suspend fun saveDistribution(distributionOfMoneyEntity: DistributionOfMoneyEntity)
    @Query("SELECT * FROM distribution_table")
    fun getDistributions() : Flow<List<DistributionOfMoneyEntity>?>
    @Query("SELECT * FROM distribution_table")
    suspend fun getAllDistributions() : List<DistributionOfMoneyEntity>?
    @Delete
    suspend fun deleteDistributions(distributionOfMoneyEntity : DistributionOfMoneyEntity)
    @Update
    suspend fun updateDistribution(distributionOfMoneyEntity: DistributionOfMoneyEntity)
    @Query("SELECT * FROM distribution_table WHERE id = :id")
    suspend  fun getDistribution(id: Int) : DistributionOfMoneyEntity
}