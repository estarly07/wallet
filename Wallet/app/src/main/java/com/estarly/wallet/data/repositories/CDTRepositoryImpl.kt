package com.estarly.wallet.data.repositories

import com.estarly.wallet.data.mappers.parseEntity
import com.estarly.wallet.data.mappers.parseModel
import com.estarly.wallet.domain.datasources.WalletDatasource
import com.estarly.wallet.domain.models.CDTModel
import com.estarly.wallet.domain.repositories.CDTRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CDTRepositoryImpl(
    private val walletDatasource: WalletDatasource
) : CDTRepository {
    override val cdts: Flow<List<CDTModel>?> = walletDatasource.cdts.map { it -> it?.map { it.parseModel() } }

    override suspend fun insertCDT(cdtModel: CDTModel) = walletDatasource.insertCDT(cdtModel.parseEntity())
    override suspend fun getCDTS(): List<CDTModel> = walletDatasource.getCDTS()?.map { it.parseModel() } ?: listOf()
    override suspend fun updateCDT(cdtModel: CDTModel) = walletDatasource.updateCDT(cdtModel.parseEntity())
    override suspend fun getCDT(id: Int): CDTModel? = walletDatasource.getCDT(id)?.parseModel()
}