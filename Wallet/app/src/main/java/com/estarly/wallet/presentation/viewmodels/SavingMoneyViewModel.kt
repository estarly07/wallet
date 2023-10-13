package com.estarly.wallet.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estarly.wallet.domain.usescases.GetSavingMoneyUseCase
import com.estarly.wallet.domain.usescases.TakeMoneyOfSavingMoneyUseCase
import com.estarly.wallet.utils.formatSalary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavingMoneyViewModel @Inject constructor(
    private val getSavingMoneyUseCase : GetSavingMoneyUseCase,
    private val takeMoneyOfSavingMoneyUseCase : TakeMoneyOfSavingMoneyUseCase
): ViewModel() {
    private var _amountSaving = MutableLiveData<String>()
    var amountSaving : LiveData<String> = _amountSaving
    var amountAvailable : Double = 0.0

    fun getAmountSaving(){
        viewModelScope.launch {
            val response = getSavingMoneyUseCase()
            _amountSaving.value = response?.amountSavingFormatted ?: "0"
            amountAvailable = response?.amountSaving ?: 0.0
        }
    }

    fun retireMoney(money: Double) {
        viewModelScope.launch {
            takeMoneyOfSavingMoneyUseCase(money)
            getAmountSaving()
        }
    }
}
