package com.estarly.wallet.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estarly.wallet.domain.usescases.GetSavingMoneyUseCase
import com.estarly.wallet.utils.formatSalary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavingMoneyViewModel @Inject constructor(
    private val getSavingMoneyUseCase : GetSavingMoneyUseCase
): ViewModel() {
    private var _amountSaving = MutableLiveData<String>()
    var amountSaving : LiveData<String> = _amountSaving

    fun getAmountSaving(){
        viewModelScope.launch {
            _amountSaving.value = getSavingMoneyUseCase()?.amountSavingFormatted ?: "0"
        }
    }
}
