package com.estarly.wallet.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estarly.wallet.R
import com.estarly.wallet.domain.models.CDTModel
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.HistoryTransactionsModel
import com.estarly.wallet.domain.usescases.CreateCdtUseCase
import com.estarly.wallet.domain.usescases.GetAllCDTSUseCase
import com.estarly.wallet.domain.usescases.GetAmountSalaryUseCase
import com.estarly.wallet.domain.usescases.GetDistributionUseCase
import com.estarly.wallet.domain.usescases.GetHistoryUseCase
import com.estarly.wallet.domain.usescases.GetRemainingMoneyUseCase
import com.estarly.wallet.domain.usescases.SaveDistributionUseCase
import com.estarly.wallet.domain.usescases.UpdateCdtUseCase
import com.estarly.wallet.domain.usescases.UpdateDistributionOfMoneyUseCase
import com.estarly.wallet.utils.formatSalary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getHistoryUseCase: GetHistoryUseCase
) : ViewModel(){
    private val _records = MutableLiveData<List<HistoryTransactionsModel>>()
    val records : LiveData<List<HistoryTransactionsModel>> = _records

    fun getRecords(){
        viewModelScope.launch {
           _records.value = getHistoryUseCase()!!
        }
    }
}