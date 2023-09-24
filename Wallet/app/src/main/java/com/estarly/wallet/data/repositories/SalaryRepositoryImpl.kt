package com.estarly.wallet.data.repositories

import com.estarly.wallet.data.mappers.parseEntity
import com.estarly.wallet.data.mappers.parseModel
import com.estarly.wallet.domain.datasources.WalletDatasource
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.SalaryModel
import com.estarly.wallet.domain.repositories.SalaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SalaryRepositoryImpl(
    private val walletDatasource: WalletDatasource
) : SalaryRepository {
    override val salary: Flow<SalaryModel?> = walletDatasource.salary.map { it?.parseModel() }
    override suspend fun saveSalary(salaryModel: SalaryModel) = walletDatasource.saveSalary(salaryModel.parseEntity())
    override suspend fun updateSalary(salaryModel: SalaryModel)  = walletDatasource.updateSalary(salaryModel.parseEntity())
    override suspend fun getSalary(): SalaryModel? = walletDatasource.getSalary()?.parseModel()
}