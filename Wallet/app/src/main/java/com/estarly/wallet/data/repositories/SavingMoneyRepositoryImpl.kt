package com.estarly.wallet.data.repositories

import com.estarly.wallet.data.mappers.parseEntity
import com.estarly.wallet.data.mappers.parseModel
import com.estarly.wallet.domain.datasources.WalletDatasource
import com.estarly.wallet.domain.models.SavinMoneyModel
import com.estarly.wallet.domain.repositories.SavingMoneyRepository

class SavingMoneyRepositoryImpl(
    private val walletDatasource: WalletDatasource
) : SavingMoneyRepository {
    override suspend fun updateMoney(savingMoney:SavinMoneyModel) = walletDatasource.updateSavingMoney(savingMoney.parseEntity())

    override suspend fun saveMoney(savingMoney:SavinMoneyModel) = walletDatasource.saveSavingMoney(savingMoney.parseEntity())

    override suspend fun getMoney() : SavinMoneyModel? = walletDatasource.getSavingMoney()?.parseModel()
}