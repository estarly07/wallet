package com.estarly.wallet.data.repositories

import com.estarly.wallet.data.mappers.parseEntity
import com.estarly.wallet.data.mappers.parseModel
import com.estarly.wallet.domain.datasources.WalletDatasource
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.repositories.DistributionOfMoneyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DistributionOfMoneyRepositoryImpl(
    private val walletDatasource: WalletDatasource
) : DistributionOfMoneyRepository {
    override val distributions : Flow<List<DistributionOfMoneyModel>?> = walletDatasource.distributions.map { it -> it?.map { it.parseModel() } }
    override suspend fun saveDistribution(distributionOfMoneyModel: DistributionOfMoneyModel) = walletDatasource.saveDistribution(distributionOfMoneyModel.parseEntity())
    override suspend fun getAllDistributions(): List<DistributionOfMoneyModel>? = walletDatasource.getAllDistributions()?.map { it.parseModel()}
    override suspend fun updateDistribution(distributionOfMoneyModel: DistributionOfMoneyModel) = walletDatasource.updateDistribution(distributionOfMoneyModel.parseEntity())
    override suspend fun getDistribution(id: Int) = walletDatasource.getDistribution(id).parseModel()
    override suspend fun deleteDistribution(distributionOfMoneyModel: DistributionOfMoneyModel) { walletDatasource.deleteDistribution(distributionOfMoneyModel.parseEntity())}
}