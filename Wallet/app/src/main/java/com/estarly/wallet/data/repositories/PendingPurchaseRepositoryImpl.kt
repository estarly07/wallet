package com.estarly.wallet.data.repositories

import com.estarly.wallet.data.mappers.parseEntity
import com.estarly.wallet.data.mappers.parseModel
import com.estarly.wallet.domain.datasources.WalletDatasource
import com.estarly.wallet.domain.models.PendingPurchaseModel
import com.estarly.wallet.domain.repositories.PendingPurchaseRepository

class PendingPurchaseRepositoryImpl(
    private val walletDatasource: WalletDatasource
) : PendingPurchaseRepository {
    override suspend fun insertPendingPurchase(pendingPurchaseModel: PendingPurchaseModel) = walletDatasource.insertPendingPurchase(pendingPurchaseModel.parseEntity())
    override suspend fun getPendingPurchases(): List<PendingPurchaseModel> = walletDatasource.getPendingPurchases()?.map { it.parseModel() } ?: listOf()
    override suspend fun updatePendingPurchases(pendingPurchaseModel: PendingPurchaseModel) = walletDatasource.updatePendingPurchases(pendingPurchaseModel.parseEntity())
    override suspend fun deletePendingPurchase(pendingPurchaseModel: PendingPurchaseModel) = walletDatasource.deletePendingPurchase(pendingPurchaseModel.parseEntity())
}