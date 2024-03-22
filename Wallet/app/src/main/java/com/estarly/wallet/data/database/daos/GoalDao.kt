package com.estarly.wallet.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.estarly.wallet.data.database.entities.GoalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {
    @Insert
    suspend fun insertGoal(goalEntity: GoalEntity)
    @Update
    suspend fun updateGoal(goalEntity: GoalEntity)
    @Query("SELECT * FROM goal_table")
    fun getGoals(): Flow<List<GoalEntity>?>
    @Query("SELECT * FROM goal_table WHERE id=:idGoal")
    suspend fun getGoal(idGoal : Int): GoalEntity?
    @Delete
    suspend fun deleteGoal(goalEntity: GoalEntity)
}
