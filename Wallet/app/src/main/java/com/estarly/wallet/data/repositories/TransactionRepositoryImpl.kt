package com.estarly.wallet.data.repositories

import com.estarly.wallet.data.mappers.parseEntity
import com.estarly.wallet.data.mappers.parseModel
import com.estarly.wallet.domain.datasources.WalletDatasource
import com.estarly.wallet.domain.models.TransactionModel
import com.estarly.wallet.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl (
    private val walletDatasource: WalletDatasource
) : TransactionRepository {
    override val theLastThreeTransactions: Flow<List<TransactionModel>?> = walletDatasource.theLastThreeTransactions.map { it -> it?.map { it.parseModel() } }
    override suspend fun insertTransaction(transactionsModel: TransactionModel) = walletDatasource.insertTransaction(transactionsModel.parseEntity())
    override suspend fun getAllTransaction(): List<TransactionModel> = walletDatasource.getAllTransaction()?.map { it.parseModel() } ?: listOf()
}