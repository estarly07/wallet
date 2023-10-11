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

class SavingMoneyBottomSheetDialog {
    companion object {
        private var distribution : DistributionOfMoneyModel? = null
        private var availableMoney : Double = 0.0
        @SuppressLint("MissingInflatedId")
        fun showBottomSheetDialog(
            activity: Activity,
            availableMoney : Double,
            onDismiss: () -> Unit,
            onSavingMoney: (Double) -> Unit,
        ) {
            this.availableMoney = availableMoney
            val bottomSheetDialog = BottomSheetDialog(activity)
            val view = activity.layoutInflater.inflate(R.layout.dialog_to_saving_money, null)
            val amount = view.findViewById<EditText>(R.id.edtAmount)
            view.findViewById<ImageButton>(R.id.btnCloseDialog).setOnClickListener { bottomSheetDialog.dismiss() }
            amount.addTextChangedListener {
                if(it.toString().isEmpty()) return@addTextChangedListener
                amount.error = if(availableMoney < it.toString().toDouble()){
                    "El monto disponible es ${availableMoney.formatSalary()}"
                } else null
            }
            view.findViewById<Button>(R.id.btnSavingMoney).setOnClickListener {
                if(amount.error != null) return@setOnClickListener
                onSavingMoney(amount.text.toString().toDouble())
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.setOnDismissListener {onDismiss()}
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }
    }
}