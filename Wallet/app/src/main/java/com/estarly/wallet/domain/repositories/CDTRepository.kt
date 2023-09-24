package com.estarly.wallet.domain.repositories

import com.estarly.wallet.domain.models.CDTModel
import kotlinx.coroutines.flow.Flow

interface CDTRepository {
    val cdts : Flow<List<CDTModel>?>
    suspend fun insertCDT(cdtModel: CDTModel)
    suspend fun getCDTS():List<CDTModel>
    suspend fun updateCDT(cdtModel: CDTModel)
    suspend fun getCDT(id: Int) : CDTModel?
}