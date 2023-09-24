package com.estarly.wallet.domain.repositories

import com.estarly.wallet.domain.models.SalaryModel
import kotlinx.coroutines.flow.Flow

interface SalaryRepository {
    val salary : Flow<SalaryModel?>
    suspend fun saveSalary(salaryModel: SalaryModel)
    suspend fun updateSalary(salaryModel: SalaryModel)
    suspend fun getSalary() : SalaryModel?
}