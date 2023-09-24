package com.estarly.wallet.domain.usescases

import com.estarly.wallet.domain.models.CDTModel
import com.estarly.wallet.domain.models.DebtModel
import com.estarly.wallet.domain.repositories.CDTRepository
import com.estarly.wallet.domain.repositories.DebtRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCDTSUseCase @Inject constructor(
    private val  cdtRepository: CDTRepository
) {
    suspend operator fun invoke(): Flow<List<CDTModel>?> = cdtRepository.cdts
}