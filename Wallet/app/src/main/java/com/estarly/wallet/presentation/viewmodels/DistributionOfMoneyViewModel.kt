package com.estarly.wallet.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.usescases.DeleteDistributionOfMoneyUseCase
import com.estarly.wallet.domain.usescases.GetAmountSalaryUseCase
import com.estarly.wallet.domain.usescases.GetDistributionsOfMoneyUseCase
import com.estarly.wallet.domain.usescases.GetRemainingMoneyUseCase
import com.estarly.wallet.domain.usescases.GetSalaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DistributionOfMoneyViewModel @Inject constructor(
    private val getDistributionsOfMoneyUseCase: GetDistributionsOfMoneyUseCase,
    private val getRemainingMoneyUseCase: GetRemainingMoneyUseCase,
    private val getSalaryUseCase: GetSalaryUseCase,
    private val getAmountSalaryUseCase: GetAmountSalaryUseCase,
    private val deleteDistributionOfMoneyUseCase: DeleteDistributionOfMoneyUseCase,
) : ViewModel(){
    private val _listDistributionOfMoney = MutableLiveData<List<DistributionOfMoneyModel>>()
    val listDistributionOfMoney : LiveData<List<DistributionOfMoneyModel>> = _listDistributionOfMoney
    private val _remainingMoney = MutableLiveData<Double>()
    val remainingMoney : LiveData<Double> = _remainingMoney


    fun getDistributions(){
        viewModelScope.launch {
            getDistributionsOfMoneyUseCase()
                .map { it ?: listOf() }
                .collect{
                    _listDistributionOfMoney.value = it
                    _remainingMoney.value = getRemainingMoneyUseCase(getAmountSalaryUseCase())!!
                }
        }
    }
    fun deleteDistribution(idDistribution : Int, position : Int){
        viewModelScope.launch {
            deleteDistributionOfMoneyUseCase(idDistribution)
        }
    }
    fun getRemainingMoney(){
        viewModelScope.launch{
            getSalaryUseCase()
                .map { it?.amount ?: 0.0 }
                .collect {
                    _remainingMoney.value = getRemainingMoneyUseCase(it)!!
                }
        }
    }
}