package com.estarly.wallet.data.repositories

import com.estarly.wallet.data.mappers.parseEntity
import com.estarly.wallet.data.mappers.parseModel
import com.estarly.wallet.domain.datasources.WalletDatasource
import com.estarly.wallet.domain.models.DebtModel
import com.estarly.wallet.domain.repositories.DebtRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DebtRepositoryImpl(
    private val walletDatasource: WalletDatasource
) : DebtRepository {
    override val debts: Flow<List<DebtModel>?> = walletDatasource.debts.map { it -> it?.map { it.parseModel() } }

    override suspend fun insertDebt(debtModel: DebtModel) = walletDatasource.insertDebt(debtModel.parseEntity())
    override suspend fun getDebts(): List<DebtModel> = walletDatasource.getDebts()?.map { it.parseModel() } ?: listOf()
    override suspend fun getDebt(idDebt: Int): DebtModel = walletDatasource.getDebt(idDebt).parseModel()
    override suspend fun updateDebt(debtModel: DebtModel) = walletDatasource.updateDebt(debtModel.parseEntity())
    override suspend fun deleteDebt(debtModel: DebtModel) = walletDatasource.deleteDebt(debtModel.parseEntity())
    override suspend fun getDebtsDoNotFinished(): List<DebtModel> = walletDatasource.getDebtsDoNotFinished()?.map { it.parseModel() } ?: listOf()
}