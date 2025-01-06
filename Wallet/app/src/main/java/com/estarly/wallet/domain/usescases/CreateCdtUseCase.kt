package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.CDTModel
import com.estarly.wallet.domain.repositories.CDTRepository
import javax.inject.Inject

class CreateCdtUseCase @Inject constructor(
    private val cdtRepository: CDTRepository
) {
    suspend operator fun invoke(
        amount    : String,
        tea       : Double,
        tna       : Double,
        time      : Int,
        image     : Int,
        rteFuente : Double?
    ){
        cdtRepository.insertCDT(CDTModel(
            id     = 0,
            amount = amount.toDouble(),
            dateLastPaid = System.currentTimeMillis(),
            tea   = tea,
            tna   = tna,
            days  = time,
            rteFuente = rteFuente,
            image = image))
    }
}