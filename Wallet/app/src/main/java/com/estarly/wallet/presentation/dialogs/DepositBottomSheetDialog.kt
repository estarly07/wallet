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
import com.estarly.wallet.presentation.adapters.DistributionSelectAdapter
import com.estarly.wallet.utils.formatSalary
import com.google.android.material.bottomsheet.BottomSheetDialog

class DepositBottomSheetDialog {
    companion object {
        private var distribution : DistributionOfMoneyModel? = null
        private var availableMoney : Double = 0.0
        @SuppressLint("MissingInflatedId")
        fun showBottomSheetDialog(
            activity: Activity,
            distributions: List<DistributionOfMoneyModel>,
            onDeposit: (String,String?, DistributionOfMoneyModel?) -> Unit,
            isTakeOfMoney : Boolean = false,
            availableMoney : Double =0.0,
            onDismiss: () -> Unit,
        ) {
            this.availableMoney = availableMoney
            val bottomSheetDialog = BottomSheetDialog(activity)
            val view = activity.layoutInflater.inflate(R.layout.dialog_to_deposit, null)
            val btnDepositOrTakeOfMoney = view.findViewById<Button>(R.id.btnDepositOrTakeOfMoney)
            val txtDescription = view.findViewById<TextView>(R.id.txtDescriptionDialog)
            val txtTitle = view.findViewById<TextView>(R.id.txtTitleDialog)
            val amount = view.findViewById<EditText>(R.id.edtAmount)
            val edtWhyTakeOfMoney = view.findViewById<EditText>(R.id.edtWhyTakeOfMoney)
            val txtWhyTakeOfMoney = view.findViewById<TextView>(R.id.txtWhyTakeOfMoney)

            if(isTakeOfMoney) {
                edtWhyTakeOfMoney.visibility = View.VISIBLE
                txtWhyTakeOfMoney.visibility = View.VISIBLE
            }
            btnDepositOrTakeOfMoney.text = if(isTakeOfMoney) "Retirar" else "Depositar"
            txtTitle.text = if(isTakeOfMoney) "¿De donde quieres sacar el dinero?" else "¿Donde quieres depositar?"
            txtDescription.text = if(isTakeOfMoney) "Si no seleccionas uno se retirara del disponible" else "Si no seleccionas uno se guardara en el disponible"

            if(isTakeOfMoney)
                amount.addTextChangedListener {
                    if(amount.text.isEmpty()) return@addTextChangedListener
                    amount.error = validate(amount.text.toString().toDouble(),this.availableMoney)
                }

            view.findViewById<ImageButton>(R.id.btnCloseDialog).setOnClickListener { bottomSheetDialog.dismiss() }
            btnDepositOrTakeOfMoney.setOnClickListener {
                if(amount.text.isEmpty()) return@setOnClickListener
                if(isTakeOfMoney) {
                    val error = validate(amount.text.toString().toDouble(), this.availableMoney)
                    amount.error = error
                    if (error!=null) return@setOnClickListener

                }
                val whyTakeOfMoney = edtWhyTakeOfMoney.text.toString().ifEmpty { null }
                onDeposit(
                    amount.text.toString(),
                    if(isTakeOfMoney) whyTakeOfMoney else null,
                    distribution)
                distribution = null
                bottomSheetDialog.dismiss()
            }
            val recycler = view.findViewById<RecyclerView>(R.id.recyclerDistribution)
            recycler.layoutManager = GridLayoutManager(activity,2)
            recycler.adapter = DistributionSelectAdapter(
                distributions,
            ) { value, position ->
                distribution = value
                if(isTakeOfMoney){
                    this.availableMoney = distribution?.amountSaved ?: availableMoney
                    if(amount.text.toString().isEmpty()) {return@DistributionSelectAdapter}
                    amount.error = validate(amount.text.toString().toDouble(),this.availableMoney)
                }
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