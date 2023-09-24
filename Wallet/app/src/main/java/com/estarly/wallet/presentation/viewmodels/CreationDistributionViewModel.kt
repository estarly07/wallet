package com.estarly.wallet.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estarly.wallet.R
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.usescases.GetAmountSalaryUseCase
import com.estarly.wallet.domain.usescases.GetDistributionUseCase
import com.estarly.wallet.domain.usescases.GetRemainingMoneyUseCase
import com.estarly.wallet.domain.usescases.SaveDistributionUseCase
import com.estarly.wallet.domain.usescases.UpdateDistributionOfMoneyUseCase
import com.estarly.wallet.utils.formatSalary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreationDistributionViewModel @Inject constructor(
    private val saveDistributionUseCase: SaveDistributionUseCase,
    private val getAmountSalaryUseCase: GetAmountSalaryUseCase,
    private val getRemainingMoneyUseCase: GetRemainingMoneyUseCase,
    private val getDistributionUseCase: GetDistributionUseCase,
    private val updateDistributionOfMoneyUseCase: UpdateDistributionOfMoneyUseCase
) : ViewModel(){
    private val _changeBackgroundDistribution = MutableLiveData<Int>()
    val changeBackgroundDistribution : LiveData<Int> = _changeBackgroundDistribution
    private val _closeActivity = MutableLiveData<Boolean>()
    val closeActivity : LiveData<Boolean> = _closeActivity
    private val _errorAmount = MutableLiveData<String>()
    val errorAmount : LiveData<String> = _errorAmount

    //si se va actualizar
    private val _distributionUpdate = MutableLiveData<DistributionOfMoneyModel>()
    val distributionUpdate : LiveData<DistributionOfMoneyModel> = _distributionUpdate

    fun getDistribution(id: Int){
        viewModelScope.launch {
            _distributionUpdate.value = getDistributionUseCase(id)!!
        }
    }

    fun changeAmount(amount : String){
        viewModelScope.launch {
            var remainingMoney = getRemainingMoneyUseCase(getAmountSalaryUseCase())
            if(_distributionUpdate.value != null){
                remainingMoney += _distributionUpdate.value!!.amountSaved
            }
            _errorAmount.value =
                if(amount.isNotEmpty() && (remainingMoney - amount.toDouble()) <=0.0)
                    "El salario disponible es: \n$${remainingMoney.formatSalary()}"
                else null
        }
    }

    fun saveDistribution(name : String, amount : String) {
        viewModelScope.launch {
            if(name.trim().isEmpty()) return@launch
            if(_distributionUpdate.value != null){
                updateDistribution(name,amount)
                return@launch
            }
            saveDistributionUseCase(DistributionOfMoneyModel(
                id          = 0,
                amountSaved = if(amount.isNotEmpty()) amount.toDouble() else 0.0,
                name        = name.trim(),
                image       = _changeBackgroundDistribution.value ?: R.drawable.one
            ))
            _closeActivity.value = true
        }
    }

    private fun updateDistribution(name: String, amount: String) {
        viewModelScope.launch {
            updateDistributionOfMoneyUseCase(DistributionOfMoneyModel(
                id          = _distributionUpdate.value!!.id,
                amountSaved = if(amount.isNotEmpty()) amount.toDouble() else 0.0,
                name        = name.trim(),
                image       = _changeBackgroundDistribution.value ?: R.drawable.one
            ))
            _closeActivity.value = true
        }
    }

    fun changeBackgroundDistribution(resource: Int) {
        _changeBackgroundDistribution.value = resource
    }

}