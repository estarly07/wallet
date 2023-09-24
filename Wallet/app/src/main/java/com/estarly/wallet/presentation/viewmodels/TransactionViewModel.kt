package com.estarly.wallet.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.TransactionModel
import com.estarly.wallet.domain.usescases.GetAllDistributionsOfMoneyUseCase
import com.estarly.wallet.domain.usescases.GetAllTransactionsUseCase
import com.estarly.wallet.domain.usescases.GetSalaryUseCase
import com.estarly.wallet.domain.usescases.GetTheLastThreeTransactionsUseCase
import com.estarly.wallet.domain.usescases.UpdateSalaryUseCase
import com.estarly.wallet.utils.formatSalary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase
) : ViewModel() {
    private val _salary = MutableLiveData<String>()
    val salary : LiveData<String> = _salary
    private val _listTransactions = MutableLiveData<List<TransactionModel>>()
    val listTransactions : LiveData<List<TransactionModel>> = _listTransactions

   fun getTransactions(){
       viewModelScope.launch {
           _listTransactions.value = getAllTransactionsUseCase()!!
       }
   }
}