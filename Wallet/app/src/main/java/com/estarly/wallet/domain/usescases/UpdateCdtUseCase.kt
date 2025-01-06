package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.repositories.CDTRepository
import javax.inject.Inject

class UpdateCdtUseCase @Inject constructor(
    private val cdtRepository: CDTRepository
) {
    suspend operator fun invoke(
        id        : Int,
        amount    : String,
        tea       : Double,
        tna       : Double,
        time      : Int,
        rteFuente : Double?,
        image : Int
    ){
        val cdtModel = cdtRepository.getCDT(id)
        cdtModel?.let {
            cdtRepository.updateCDT(it.copy(
                amount       = amount.toDouble(),
                image        = image,
                tea          = tea,
                days         = time,
                tna          = tna,
                rteFuente    = rteFuente,
                dateLastPaid = System.currentTimeMillis()
            ))
        }
    }
}