package com.estarly.wallet.presentation.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estarly.wallet.domain.models.DebtModel
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.TransactionModel
import com.estarly.wallet.domain.models.TypeTransactions
import com.estarly.wallet.domain.usescases.DistributionAutomaticUseCase
import com.estarly.wallet.domain.usescases.GetAllDebtsDoNotFinishedUseCase
import com.estarly.wallet.domain.usescases.GetAllDistributionsOfMoneyUseCase
import com.estarly.wallet.domain.usescases.GetAllTransactionsUseCase
import com.estarly.wallet.domain.usescases.GetAmountSalaryUseCase
import com.estarly.wallet.domain.usescases.GetDistributionsOfMoneyUseCase
import com.estarly.wallet.domain.usescases.GetPercentageMoneySpentCurrentMonthUseCase
import com.estarly.wallet.domain.usescases.GetRemainingMoneyUseCase
import com.estarly.wallet.domain.usescases.GetSalaryUseCase
import com.estarly.wallet.domain.usescases.GetTheLastThreeTransactionsUseCase
import com.estarly.wallet.domain.usescases.PayDebtUseCase
import com.estarly.wallet.domain.usescases.TakeMoneyOutUseCase
import com.estarly.wallet.domain.usescases.UpdateSalaryUseCase
import com.estarly.wallet.presentation.dialogs.showYesOrNoAlertDialog
import com.estarly.wallet.utils.formatSalary
import com.github.mikephil.charting.data.PieEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSalaryUseCase   : GetSalaryUseCase,
    private val updateSalaryUseCase: UpdateSalaryUseCase,
    private val getAllDistributionsOfMoneyUseCase: GetAllDistributionsOfMoneyUseCase,
    private val getTheLastThreeTransactionsUseCase: GetTheLastThreeTransactionsUseCase,
    private val takeMoneyOutUseCase: TakeMoneyOutUseCase,
    private val getAllTransactionsUseCase: GetAllTransactionsUseCase,
    private val getRemainingMoneyUseCase: GetRemainingMoneyUseCase,
    private val getDistributionsOfMoneyUseCase: GetDistributionsOfMoneyUseCase,
    private val getAmountSalaryUseCase : GetAmountSalaryUseCase,
    private val getAllDebtsDoNotFinishedUseCase: GetAllDebtsDoNotFinishedUseCase,
    private val payDebtUseCase: PayDebtUseCase,
    private val getPercentageMoneySpentCurrentMonthUseCase: GetPercentageMoneySpentCurrentMonthUseCase,
    private val distributionAutomaticUseCase: DistributionAutomaticUseCase
) : ViewModel(){
    private val _salary = MutableLiveData<String>()
    val salary : LiveData<String> = _salary
    private val _percentageSpent = MutableLiveData<String>()
    val percentageSpent : LiveData<String> = _percentageSpent
    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog : LiveData<Boolean> = _showDialog
    private val _showDialogPay = MutableLiveData<Boolean>()
    val showDialogPay : LiveData<Boolean> = _showDialogPay
    private val _lastThreeTransactions = MutableLiveData<List<TransactionModel>>()
    val lastThreeTransactions : LiveData<List<TransactionModel>> = _lastThreeTransactions
    private val _entriesEntries = MutableLiveData<List<PieEntry>>()
    val entriesEntries : LiveData<List<PieEntry>> = _entriesEntries

    var listDebts : List<DebtModel> = listOf()
    var listDistributionOfMoney : List<DistributionOfMoneyModel> = listOf()
    var isTakeOfMoney : Boolean = false
    var availableMoney : Double = 0.0
    var totalMoney : Double = 0.0

    fun getSalary(){
        viewModelScope.launch {
            getDistributions()//active because if there is a change in the distributions
            getSalaryUseCase()
                .map {
                    totalMoney = it?.amount ?: 0.0
                    getPercentageSent()
                    availableMoney = getRemainingMoneyUseCase(totalMoney)!!
                    it?.amount?.formatSalary() ?: "0"
                }
                .collect {
                    _salary.value = it
                    getValuesGraphic()
                }
        }
    }

    private fun getPercentageSent() {
        viewModelScope.launch {
            _percentageSpent.value = "${getPercentageMoneySpentCurrentMonthUseCase().toInt()}%"
        }
    }

    private fun getDistributions(){
        viewModelScope.launch {
            getDistributionsOfMoneyUseCase()
                .map { it ?: listOf() }
                .collect{
                    availableMoney = getRemainingMoneyUseCase(getAmountSalaryUseCase())!!
                }
        }
    }
    private fun getValuesGraphic(){
        viewModelScope.launch {
            val transactions = getAllTransactionsUseCase()
            val entries = listOf(
                PieEntry(1f, ""),//margin
                PieEntry(getPercentage(transactions, TypeTransactions.DEPOSIT), ""),
                PieEntry(1f, ""),//margin
                PieEntry(getPercentage(transactions, TypeTransactions.TAKE_MONEY_OUT), ""),
                PieEntry(1f, ""),//margin
                PieEntry(getPercentage(transactions, TypeTransactions.PAY_DEBT), ""),
            )
            _entriesEntries.value = entries
        }
    }
    fun showDialogPay(){
        viewModelScope.launch {
            listDistributionOfMoney = getAllDistributionsOfMoneyUseCase()!!
            listDebts = getAllDebtsDoNotFinishedUseCase()
            _showDialogPay.value = true
        }
    }

    @SuppressLint("LongLogTag")
    private fun getPercentage(transactions: List<TransactionModel>, typeTransactions: TypeTransactions): Float {
        var amountCurrent = 0.0
        var amountTotal = 0.0
        transactions.forEach {
            if(it.typeTransaction == TypeTransactions.DEPOSIT){
                amountTotal += it.amount
            }
        }
        when(typeTransactions){
            TypeTransactions.TAKE_MONEY_OUT->{
                transactions.forEach {
                    if(it.typeTransaction == TypeTransactions.TAKE_MONEY_OUT){
                        amountCurrent += it.amount
                    }
                }
                Log.i("getPercentage TAKE_MONEY_OUT","${(amountCurrent*100/amountTotal)}")
                return (amountCurrent*100/amountTotal).toFloat()
            }
            TypeTransactions.PAY_DEBT->{
                transactions.forEach {
                    if(it.typeTransaction == TypeTransactions.PAY_DEBT){
                        amountCurrent += it.amount
                    }
                }
                Log.i("getPercentage PAY_DEBT","amountCurrent $amountCurrent ${(amountCurrent*100/amountTotal)}")
                return (amountCurrent*100/amountTotal).toFloat()
            }
            else -> {
                Log.i("getPercentage","${(totalMoney*100/amountTotal)}")
                return (totalMoney * 100/amountTotal).toFloat()
            }
        }
    }

    fun getLastThreeTransactions(){
        viewModelScope.launch {
            getTheLastThreeTransactionsUseCase()
                .map { it ?: listOf() }
                .collect{_lastThreeTransactions.value = it}
        }
    }

    fun updateSalary(
        amount: String,
        whyTakeOfMoney: String?,
        distribution: DistributionOfMoneyModel?
    ) {
        viewModelScope.launch {
            if (amount.isEmpty()) return@launch
            if(isTakeOfMoney){
                takeMoneyOutUseCase(amount, whyTakeOfMoney, distribution)
            }else{
                updateSalaryUseCase(amount, distribution)
            }
        }
    }

    fun showDialog(isTakeOfMoney : Boolean = false) {
        viewModelScope.launch {
            listDistributionOfMoney = getAllDistributionsOfMoneyUseCase()!!
            this@HomeViewModel.isTakeOfMoney = isTakeOfMoney
            _showDialog.value = true
        }
    }

    fun dismissDialog() {
        _showDialog.value = false
    }
    fun dismissDialogPay() {
        _showDialogPay.value = false
    }

    fun payDebt(amount: String, distribution: DistributionOfMoneyModel?, debt: DebtModel) {
        viewModelScope.launch {
            Log.i("payDebt","$amount ${distribution?.name} ${debt.name}")
            if (amount.isEmpty()) return@launch
            payDebtUseCase(amount, distribution, debt)
        }
    }

    fun depositAutomatic(context:Context) {
        showYesOrNoAlertDialog(
            context,
            "Alerta",
            "¡Prepárate para dispersar la suma $${availableMoney.formatSalary()} en todas las configuraciones de distribución que has creado!",
            onPositive = {
                viewModelScope.launch { distributionAutomaticUseCase() }
            }
        )
    }
}