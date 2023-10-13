package com.estarly.wallet.presentation.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.estarly.wallet.R
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.utils.formatSalary
import com.google.android.material.bottomsheet.BottomSheetDialog

class SavingOrTakeOffMoneyBottomSheetDialog {
    companion object {
        private var distribution : DistributionOfMoneyModel? = null
        private var availableMoney : Double = 0.0
        @SuppressLint("MissingInflatedId")
        fun showBottomSheetDialog(
            activity: Activity,
            availableMoney : Double,
            onDismiss: () -> Unit,
            onSavingMoney: (Double) -> Unit,
            takeOfMoney : Boolean = false
        ) {
            this.availableMoney = availableMoney
            val bottomSheetDialog = BottomSheetDialog(activity)
            val view = activity.layoutInflater.inflate(R.layout.dialog_to_saving_money, null)
            val amount = view.findViewById<EditText>(R.id.edtAmount)
            val btnSavingMoney = view.findViewById<Button>(R.id.btnSavingMoney)
            view.findViewById<ImageButton>(R.id.btnCloseDialog).setOnClickListener { bottomSheetDialog.dismiss() }
            if(takeOfMoney){
                val txtTitle = view.findViewById<TextView>(R.id.txtTitleSavingMoney)
                val txtContent = view.findViewById<TextView>(R.id.txtContentSavingMoney)
                txtTitle.text = "¿Cuánto dinero deseas sacar?"
                txtContent.text = "¡Haz realidad tus planes! Retira la suma que necesitas de tus ahorros, liberándola para usarla como quieras en tu saldo disponible."
                btnSavingMoney.text = "Retirar"
            }
            amount.addTextChangedListener {
                if(it.toString().isEmpty()) return@addTextChangedListener
                amount.error = if(availableMoney < it.toString().toDouble()){
                    "El monto disponible es ${availableMoney.formatSalary()}"
                } else null
            }
            btnSavingMoney.setOnClickListener {
                if(amount.error != null || amount.text.toString().isEmpty()) return@setOnClickListener
                onSavingMoney(amount.text.toString().toDouble())
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.setOnDismissListener {onDismiss()}
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }
    }
}