package com.estarly.wallet.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estarly.wallet.R
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.PendingPurchaseModel
import com.estarly.wallet.domain.usescases.CreatePendingPurchaseUseCase
import com.estarly.wallet.domain.usescases.DeletePendingPurchaseUseCase
import com.estarly.wallet.domain.usescases.GetAllPendingPurchasesUseCase
import com.estarly.wallet.domain.usescases.GetAmountSalaryUseCase
import com.estarly.wallet.domain.usescases.GetDistributionUseCase
import com.estarly.wallet.domain.usescases.GetRemainingMoneyUseCase
import com.estarly.wallet.domain.usescases.SaveDistributionUseCase
import com.estarly.wallet.domain.usescases.UpdateDistributionOfMoneyUseCase
import com.estarly.wallet.domain.usescases.UpdatePendingPurchaseUseCase
import com.estarly.wallet.utils.formatSalary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PendingPurchaseViewModel @Inject constructor(
    private val createPendingPurchaseUseCase : CreatePendingPurchaseUseCase,
    private val getPendingPurchaseUseCase: GetAllPendingPurchasesUseCase,
    private val updatePendingPurchaseUseCase : UpdatePendingPurchaseUseCase,
    private val deletePendingPurchaseUseCase: DeletePendingPurchaseUseCase
) : ViewModel(){
    private val _pendingPurchases = MutableLiveData<List<PendingPurchaseModel>>()
    val pendingPurchases : LiveData<List<PendingPurchaseModel>> = _pendingPurchases
    private val _amountTotalPendingPurchases = MutableLiveData<String>()
    val amountTotalPendingPurchases : LiveData<String> = _amountTotalPendingPurchases

    fun getPendingPurchases(){
        viewModelScope.launch {
            _pendingPurchases.value = getPendingPurchaseUseCase()!!
            var amount = 0.0
            _pendingPurchases.value!!.map { amount += it.amount  }
            _amountTotalPendingPurchases.value = amount.formatSalary()
        }
    }
    fun createPendingPurchase(name: String,amount:String,image: String){
        viewModelScope.launch {
            createPendingPurchaseUseCase(name = name, amount = amount.toDouble(),image = image)
            getPendingPurchases()
        }
    }

    fun update(pending: PendingPurchaseModel,name: String,amount:String,image: String) {
        viewModelScope.launch {
            updatePendingPurchaseUseCase(pending.copy(name=name, amount = amount.toDouble(), image = image))
            getPendingPurchases()
        }
    }

    fun updateFinishedPurchase(pending: PendingPurchaseModel, checked: Boolean) {
        viewModelScope.launch {
            updatePendingPurchaseUseCase(pending.copy(finished = checked))
            getPendingPurchases()
        }
    }

    fun delete(pending: PendingPurchaseModel) {
        viewModelScope.launch {
            deletePendingPurchaseUseCase(pending)
            getPendingPurchases()
        }
    }


}