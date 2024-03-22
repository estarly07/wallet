package com.estarly.wallet.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estarly.wallet.R
import com.estarly.wallet.domain.models.CDTModel
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.GoalModel
import com.estarly.wallet.domain.usescases.CreateCdtUseCase
import com.estarly.wallet.domain.usescases.CreateGoalUseCase
import com.estarly.wallet.domain.usescases.DeleteGoalUseCase
import com.estarly.wallet.domain.usescases.GetAllCDTSUseCase
import com.estarly.wallet.domain.usescases.GetAllDistributionsOfMoneyUseCase
import com.estarly.wallet.domain.usescases.GetAllDistributionsOfMoneyUseCase_Factory
import com.estarly.wallet.domain.usescases.GetAllGoalsUseCase
import com.estarly.wallet.domain.usescases.GetAmountSalaryUseCase
import com.estarly.wallet.domain.usescases.GetDistributionUseCase
import com.estarly.wallet.domain.usescases.GetRemainingMoneyUseCase
import com.estarly.wallet.domain.usescases.GetSalaryUseCase
import com.estarly.wallet.domain.usescases.SaveDistributionUseCase
import com.estarly.wallet.domain.usescases.UpdateCdtUseCase
import com.estarly.wallet.domain.usescases.UpdateDistributionOfMoneyUseCase
import com.estarly.wallet.domain.usescases.UpdateGoalUseCase
import com.estarly.wallet.domain.usescases.UpdateSalaryUseCase
import com.estarly.wallet.utils.formatSalary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(
    private val getAllGoalsUseCase: GetAllGoalsUseCase,
    private val createGoalUseCase: CreateGoalUseCase,
    private val getAllDistributionsOfMoneyUseCase: GetAllDistributionsOfMoneyUseCase,
    private val getRemainingMoneyUseCase: GetRemainingMoneyUseCase,
    private val getAmountSalaryUseCase: GetAmountSalaryUseCase,
    private val updateGoalUseCase :UpdateGoalUseCase,
    private val deleteGoalUseCase :DeleteGoalUseCase,
) : ViewModel(){
    private val _listGoals = MutableLiveData<List<GoalModel>>()
    val listGoals : LiveData<List<GoalModel>> = _listGoals
    private val _showDialogPayGoal = MutableLiveData<Boolean>()
    val showDialogPayGoal : LiveData<Boolean> = _showDialogPayGoal

    var listDistributions : List<DistributionOfMoneyModel> = listOf()
    var goalModelUpdate : GoalModel? = null
    var availableMoney : Double = 0.0
    fun getGoals(){
        viewModelScope.launch {
            getAllGoalsUseCase()
                .map { it ?: listOf() }
                .collect{ _listGoals.value = it}
        }
    }

    fun createGoal(amountInitial: String?,amountTotal: String,name: String, image : String,description : String){
        viewModelScope.launch {
            createGoalUseCase(
                name = name,
                amountInitial = amountInitial?.toDouble() ?: 0.0,
                amountTotal = amountTotal.toDouble(),
                image = image,
                description = description
            )
        }
    }
    fun showDialogPayGoal(goalModel: GoalModel){
        viewModelScope.launch {
            goalModelUpdate = goalModel
            availableMoney = getRemainingMoneyUseCase(getAmountSalaryUseCase())
            listDistributions = getAllDistributionsOfMoneyUseCase()
            _showDialogPayGoal.value = true
        }
    }
    fun updateGoal(goalModel: GoalModel,amount: String, distribution: DistributionOfMoneyModel?){
        viewModelScope.launch {
            updateGoalUseCase(goalModel,amount.toDouble(),distribution)
            dismissDialogPayGoal()
        }
    }
    fun dismissDialogPayGoal(){
        goalModelUpdate = null
        availableMoney = 0.0
        _showDialogPayGoal.value = false
    }

    fun deleteGoal(idGoal: Int) {
        viewModelScope.launch {
            deleteGoalUseCase(idGoal)
        }
    }
}