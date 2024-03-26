package com.estarly.wallet.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estarly.wallet.domain.models.DebtModel
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.TransactionModel
import com.estarly.wallet.domain.usescases.DeleteDebtUseCase
import com.estarly.wallet.domain.usescases.GetAllDistributionsOfMoneyUseCase
import com.estarly.wallet.domain.usescases.GetDebtsUseCase
import com.estarly.wallet.domain.usescases.GetSalaryUseCase
import com.estarly.wallet.domain.usescases.GetTheLastThreeTransactionsUseCase
import com.estarly.wallet.domain.usescases.SaveDebtUseCase
import com.estarly.wallet.domain.usescases.UpdateSalaryUseCase
import com.estarly.wallet.utils.formatSalary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DebtViewModel @Inject constructor(
    private val saveDebtUseCase: SaveDebtUseCase,
    private val getDebtsUseCase: GetDebtsUseCase,
    private val deleteDebtUseCase: DeleteDebtUseCase,
) : ViewModel(){
    private val _listDebts = MutableLiveData<List<DebtModel>>()
    val listDebts : LiveData<List<DebtModel>> = _listDebts

    fun getDebts(){
        viewModelScope.launch {
            getDebtsUseCase()
                .map { it?: listOf() }
                .collect{_listDebts.value = it}
        }
    }

    fun saveDebt(name:String, amountInitial : String, amountPaid: String){
        viewModelScope.launch {
            if(name.isEmpty() || amountPaid.isEmpty()) return@launch
            saveDebtUseCase(
                name,
                if(amountInitial.isEmpty()) null else amountInitial.toDouble(),
                amountPaid.toDouble()
            )
        }
    }
    fun deleteDebt(debtModel: DebtModel){
        viewModelScope.launch {
            deleteDebtUseCase(debtModel.id)
        }
    }
}