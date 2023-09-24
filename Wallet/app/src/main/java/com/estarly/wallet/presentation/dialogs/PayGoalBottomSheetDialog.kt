package com.estarly.wallet.presentation.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estarly.wallet.R
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.GoalModel
import com.estarly.wallet.presentation.adapters.DistributionSelectAdapter
import com.estarly.wallet.utils.formatSalary
import com.google.android.material.bottomsheet.BottomSheetDialog

class PayGoalBottomSheetDialog {
    companion object {
        private var distribution : DistributionOfMoneyModel? = null
        private var availableMoney : Double = 0.0
        @SuppressLint("MissingInflatedId")
        fun showBottomSheetDialog(
            activity: Activity,
            goalModel: GoalModel,
            onPay : (amount: String, distribution:DistributionOfMoneyModel? )-> Unit,
            distributions: List<DistributionOfMoneyModel>,
            availableMoney : Double =0.0,
            onDismiss: () -> Unit,
        ) {
            this.availableMoney = availableMoney
            val bottomSheetDialog = BottomSheetDialog(activity)
            val view = activity.layoutInflater.inflate(R.layout.dialog_to_pay_goal, null)
            val btnDepositOrTakeOfMoney = view.findViewById<Button>(R.id.btnDepositOrTakeOfMoney)
            val amount = view.findViewById<EditText>(R.id.edtAmount)
            view.findViewById<TextView>(R.id.txtTitleDialogGoal).setText("Guarda dinero en ${goalModel.name}")
            amount.addTextChangedListener {
                if(amount.text.isEmpty()) return@addTextChangedListener
                amount.error = validate(
                    amount.text.toString().toDouble(),
                    this.availableMoney + goalModel.amountSaved
                )
            }
            amount.setText(goalModel.amountSaved.toString())
            view.findViewById<ImageButton>(R.id.btnCloseDialog).setOnClickListener { bottomSheetDialog.dismiss() }
            btnDepositOrTakeOfMoney.setOnClickListener {
                if(amount.text.isEmpty()) return@setOnClickListener
                val error = validate(
                    amount.text.toString().toDouble(),
                    this.availableMoney + goalModel.amountSaved
                )
                amount.error = error
                if (error!=null) return@setOnClickListener
                onPay(amount.text.toString(), distribution)
                distribution = null
                bottomSheetDialog.dismiss()
            }
            val recycler = view.findViewById<RecyclerView>(R.id.recyclerDistribution)
            recycler.layoutManager = GridLayoutManager(activity,2)
            recycler.adapter = DistributionSelectAdapter(
                distributions,
            ) { value, position ->
                distribution = value
                this.availableMoney = distribution?.amountSaved ?: availableMoney
                if(amount.text.toString().isEmpty()) {return@DistributionSelectAdapter}
                amount.error = validate(
                    amount.text.toString().toDouble(),
                    this.availableMoney + goalModel.amountSaved
                )
            }
            bottomSheetDialog.setOnDismissListener {onDismiss()}
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }
        private fun validate(currentSalary : Double, salaryLimit : Double) : String? {
            if(salaryLimit < currentSalary){
                return "El monto disponible es ${salaryLimit.formatSalary()}"
            }
            return null
        }
    }
}