package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.PendingPurchaseModel
import com.estarly.wallet.domain.repositories.PendingPurchaseRepository
import javax.inject.Inject

class GetAllPendingPurchasesUseCase @Inject constructor(
    private val pendingPurchaseRepository: PendingPurchaseRepository
) {
    suspend operator fun invoke(): List<PendingPurchaseModel> = pendingPurchaseRepository.getPendingPurchases()
}