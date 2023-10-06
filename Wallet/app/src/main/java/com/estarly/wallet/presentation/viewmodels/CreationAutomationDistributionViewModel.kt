package com.estarly.wallet.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.usescases.GetAllDistributionsOfMoneyUseCase
import com.estarly.wallet.domain.usescases.UpdateDistributionOfMoneyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreationAutomationDistributionViewModel @Inject constructor(
    private val getAllDistributionsOfMoneyUseCase: GetAllDistributionsOfMoneyUseCase,
    private val updateDistributionOfMoneyUseCase: UpdateDistributionOfMoneyUseCase
) : ViewModel(){
    private val _listDistributionOfMoney = MutableLiveData<List<DistributionOfMoneyModel>>()
    val listDistributionOfMoney : LiveData<List<DistributionOfMoneyModel>> = _listDistributionOfMoney

    fun getDistributions(){
        viewModelScope.launch {
            _listDistributionOfMoney.value = getAllDistributionsOfMoneyUseCase()!!
        }
    }
    fun updateExpectedAmountDistribution(
        distributionOfMoneyModel: DistributionOfMoneyModel,
        amount: String
    ){
        viewModelScope.launch {
            updateDistributionOfMoneyUseCase(distribution = distributionOfMoneyModel.copy(amountExpected = amount.ifEmpty { "0" }.toDouble()))
        }
    }
}