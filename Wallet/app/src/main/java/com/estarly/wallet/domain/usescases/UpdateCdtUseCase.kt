package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.repositories.CDTRepository
import javax.inject.Inject

class UpdateCdtUseCase @Inject constructor(
    private val cdtRepository: CDTRepository
) {
    suspend operator fun invoke(
        id: Int,
        amount: String,
        image: Int
    ){
        val cdtModel = cdtRepository.getCDT(id)
        cdtModel?.let {
            cdtRepository.updateCDT(it.copy(
                amount = amount.toDouble(),
                image = image,
                dateLastPaid = System.currentTimeMillis()
            ))
        }
    }
}