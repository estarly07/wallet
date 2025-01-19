package com.estarly.wallet.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estarly.wallet.R
import com.estarly.wallet.domain.models.DebtModel
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.TypeTransactions
import com.estarly.wallet.domain.usescases.GetAllDebtsDoNotFinishedUseCase
import com.estarly.wallet.domain.usescases.GetAllDistributionsOfMoneyUseCase
import com.estarly.wallet.domain.usescases.GetAmountSalaryUseCase
import com.estarly.wallet.domain.usescases.GetRemainingMoneyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeyboardViewModel @Inject constructor(
    private val getAllDistributionsOfMoneyUseCase: GetAllDistributionsOfMoneyUseCase,
    private val getRemainingMoneyUseCase: GetRemainingMoneyUseCase,
    private val getAmountSalaryUseCase : GetAmountSalaryUseCase,
    private val getAllDebtsDoNotFinishedUseCase : GetAllDebtsDoNotFinishedUseCase,
) : ViewModel(){
    private val _amount = MutableLiveData("")
    val amount: LiveData<String> = _amount
    private val _validAmount = MutableLiveData<Boolean>()
    val validAmount: LiveData<Boolean> = _validAmount
    private val _distributions = MutableLiveData<List<DistributionOfMoneyModel>>()
    val distributions: LiveData<List<DistributionOfMoneyModel>> = _distributions
    private val _distributionSelect = MutableLiveData<DistributionOfMoneyModel>()
    val distributionSelect: LiveData<DistributionOfMoneyModel> = _distributionSelect
    private val _debts = MutableLiveData<List<DebtModel>>()
    val debts: LiveData<List<DebtModel>> = _debts
    private val _debtSelect = MutableLiveData<DebtModel>()
    val debtSelect: LiveData<DebtModel> = _debtSelect
    private lateinit var availableAmount : DistributionOfMoneyModel
    private lateinit var typeTransaction: TypeTransactions
    init {
        getAvailableAmount()
        getDistributions()
    }

    private fun getAvailableAmount() {
        viewModelScope.launch {
            val amount = getRemainingMoneyUseCase.invoke(getAmountSalaryUseCase())
            availableAmount = DistributionOfMoneyModel(
                id          = -1,
                amountSaved = amount,
                name        = "Disponible",
                image       = R.drawable.one,
                amountExpected = 0.0,
                amountExpectedFormatted = ""
            )
            _distributionSelect.postValue(availableAmount)
        }
    }

    private fun getDistributions() {
        viewModelScope.launch {
            _distributions.postValue(getAllDistributionsOfMoneyUseCase.invoke())
        }
    }

    fun appendCharacter(character: String) {
        _amount.value = (_amount.value ?: "") + character
        validateAmount()
    }

    private fun validateAmount() {
        if(_amount.value.isNullOrEmpty()){
            _validAmount.value = false
            return
        }
        if(typeTransaction == TypeTransactions.DEPOSIT){
            _validAmount.value = true
            return
        }
        val currentAmount = _amount.value.toString().toDouble()
        var isValid = currentAmount <= (distributionSelect.value?.amountSaved ?: availableAmount.amountSaved)
        if(typeTransaction == TypeTransactions.PAY_DEBT && isValid){
            debtSelect.value?.let {
                isValid = currentAmount <= debtSelect.value!!.missingAmount
            }
        }
        _validAmount.value = isValid
    }

    fun deleteLastCharacter() {
        _amount.value = (_amount.value ?: "").dropLast(1)
        validateAmount()
    }
    fun selectDistribution(distributionOfMoneyModel: DistributionOfMoneyModel?) {
        _distributionSelect.value = distributionOfMoneyModel ?: availableAmount
        validateAmount()
    }

    fun setTypeTransaction(typeTransaction: TypeTransactions) {
        this.typeTransaction = typeTransaction
        if(this.typeTransaction == TypeTransactions.PAY_DEBT){
            getDebts()
        }
    }

    private fun getDebts() {
        viewModelScope.launch {
            val debts = getAllDebtsDoNotFinishedUseCase.invoke()
            selectDebt(debts.first())
            _debts.postValue(debts)
        }
    }
    fun selectDebt(debtModel: DebtModel){
        _debtSelect.value = debtModel
        validateAmount()
    }
}