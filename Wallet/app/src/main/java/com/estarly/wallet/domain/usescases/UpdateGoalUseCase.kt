package com.estarly.wallet.domain.usescases

import android.util.Log
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.GoalModel
import com.estarly.wallet.domain.models.SalaryModel
import com.estarly.wallet.domain.models.TypeTransactions
import com.estarly.wallet.domain.repositories.GoalRepository
import com.estarly.wallet.domain.repositories.SalaryRepository
import javax.inject.Inject

class UpdateGoalUseCase @Inject constructor(
    private val salaryRepository: SalaryRepository,
    private val goalRepository: GoalRepository,
    private val updateDistributionOfMoneyUseCase: UpdateDistributionOfMoneyUseCase,
) {
    suspend operator fun invoke(
        goalModel: GoalModel,
        amount: Double,
        distribution: DistributionOfMoneyModel?
    ){
        val salaryModel = salaryRepository.getSalary()
        val newAmountDepositInGoal: Double
        if(goalModel.amountSaved > amount){
            newAmountDepositInGoal = (goalModel.amountSaved - amount)
            salaryModel!!.amount += newAmountDepositInGoal
            Log.i("updateSalary","se quito esta cantidad de la meta ${(goalModel.amountSaved - amount)}")
        }else{
            newAmountDepositInGoal = (amount - goalModel.amountSaved)
            salaryModel!!.amount -= newAmountDepositInGoal
            Log.i("updateSalary","se agrego esta cantidad de la meta ${(amount - goalModel.amountSaved)}")
            if(distribution!=null){
                val newAmount = distribution.amountSaved - newAmountDepositInGoal
                Log.i("updateSalary","se quito de la distribuccion esta cantidad y este queda con ${newAmount}")
                updateDistributionOfMoneyUseCase(distribution.copy(amountSaved = newAmount))
            }else{
                Log.i("updateSalary","se quito del disponible y queda con ${salaryModel!!.amount}")
            }
        }
        Log.i("updateSalary","${(salaryModel?.amount)}")

        salaryRepository.updateSalary(salaryModel!!)
        goalRepository.updateGoal(goalModel.copy(amountSaved = amount))
    }
}