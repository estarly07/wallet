package com.estarly.wallet.presentation.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estarly.wallet.R
import com.estarly.wallet.domain.models.DebtModel
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.presentation.adapters.DebtsAdapter
import com.estarly.wallet.presentation.adapters.DistributionSelectAdapter
import com.estarly.wallet.utils.formatSalary
import com.google.android.material.bottomsheet.BottomSheetDialog

class PayBottomSheetDialog {
    companion object {
        private var distribution : DistributionOfMoneyModel? = null
        private var debt : DebtModel? = null
        private var availableMoney : Double = 0.0
        @SuppressLint("MissingInflatedId")
        fun showBottomSheetDialog(
            activity: Activity,
            distributions: List<DistributionOfMoneyModel>,
            debts: List<DebtModel>,
            onPay: (String, DistributionOfMoneyModel?, DebtModel) -> Unit,
            availableMoney : Double = 0.0,
            onDismiss: () -> Unit,
        ) {
            this.availableMoney = availableMoney
            this.debt = debts.first()
            val bottomSheetDialog = BottomSheetDialog(activity)
            val view = activity.layoutInflater.inflate(R.layout.dialog_to_pay, null)
            val btnPay = view.findViewById<Button>(R.id.btnPay)
            val amount = view.findViewById<EditText>(R.id.edtAmount)
            amount.addTextChangedListener {
                if(amount.text.isEmpty()) return@addTextChangedListener
                amount.error = validate(amount.text.toString().toDouble(), this.availableMoney)
            }


            view.findViewById<ImageButton>(R.id.btnCloseDialog).setOnClickListener { bottomSheetDialog.dismiss() }
            btnPay.setOnClickListener {
                if(amount.text.isEmpty()) return@setOnClickListener
                val error = validate(amount.text.toString().toDouble(), this.availableMoney)
                amount.error = error
                if (error!=null || debt== null) return@setOnClickListener
                //validate debt
                val messageDebtError = validateDebt(amount.text.toString().toDouble(),debt!!.missingAmount)
                amount.error = messageDebtError
                if(messageDebtError!=null){ return@setOnClickListener }
                onPay(amount.text.toString(), distribution, debt!!)
                distribution = null
                debt = null
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
                amount.error = validate(amount.text.toString().toDouble(),this.availableMoney)
            }
            val spnDebts = view.findViewById<Spinner>(R.id.spnDebts)
            val adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, debts.map { "${it.name} (${it.missingAmount.formatSalary()})" })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spnDebts.adapter = adapter
            spnDebts.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    debt = debts[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
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
        private fun validateDebt(currentDebt : Double, debtLimit : Double) : String? {
            if(debtLimit < currentDebt){
                return "El monto faltante de la deuda es ${debtLimit.formatSalary()}"
            }
            return null
        }
    }
}