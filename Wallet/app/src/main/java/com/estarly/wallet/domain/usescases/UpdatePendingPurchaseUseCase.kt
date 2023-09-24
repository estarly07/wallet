package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.PendingPurchaseModel
import com.estarly.wallet.domain.repositories.PendingPurchaseRepository
import javax.inject.Inject

class UpdatePendingPurchaseUseCase @Inject constructor(
    private val pendingPurchaseRepository: PendingPurchaseRepository
) {
    suspend operator fun invoke(pendingPurchaseModel: PendingPurchaseModel){ pendingPurchaseRepository.updatePendingPurchases(pendingPurchaseModel) }
}