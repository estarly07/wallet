package com.estarly.wallet.domain.repositories

import com.estarly.wallet.domain.models.PendingPurchaseModel

interface PendingPurchaseRepository {
    suspend fun insertPendingPurchase(pendingPurchaseModel: PendingPurchaseModel)
    suspend fun getPendingPurchases():List<PendingPurchaseModel>
    suspend fun updatePendingPurchases(pendingPurchaseModel: PendingPurchaseModel)
}