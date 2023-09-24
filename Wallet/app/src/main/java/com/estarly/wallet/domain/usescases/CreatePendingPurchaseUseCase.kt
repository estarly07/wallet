package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.PendingPurchaseModel
import com.estarly.wallet.domain.repositories.PendingPurchaseRepository
import javax.inject.Inject

class CreatePendingPurchaseUseCase @Inject constructor(
    private val pendingPurchaseRepository: PendingPurchaseRepository
) {
    suspend operator fun invoke(
        name: String,
        amount: Double,
        image: String
    ){
        pendingPurchaseRepository.insertPendingPurchase(
            PendingPurchaseModel(
                id     = 0,
                name   = name,
                amount = amount,
                image  = image,
                finished = false,

            )
        )
    }
}