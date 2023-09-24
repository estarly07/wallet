package com.estarly.wallet.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.estarly.wallet.data.database.entities.SalaryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SalaryDao {
    @Insert
    suspend fun saveSalary(salaryEntity: SalaryEntity)
    @Update
    suspend fun updateSalary(salaryEntity: SalaryEntity)
    @Query("SELECT * FROM salary_table LIMIT 1")
    fun getSalary() : Flow<SalaryEntity?>
    @Query("SELECT * FROM salary_table LIMIT 1")
    suspend fun getSalaryNoFlow() : SalaryEntity?
}