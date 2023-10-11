package com.estarly.wallet.domain.repositories

import com.estarly.wallet.domain.models.SavinMoneyModel

interface SavingMoneyRepository {
     suspend fun updateMoney(savingMoney:SavinMoneyModel)
     suspend fun saveMoney(savingMoney:SavinMoneyModel)
     suspend fun getMoney() : SavinMoneyModel?
}