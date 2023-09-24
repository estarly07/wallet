package com.estarly.wallet.presentation.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estarly.wallet.R
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.presentation.adapters.DistributionSelectAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class CreateDebtSheetDialog {
    companion object {
        @SuppressLint("MissingInflatedId")
        fun showBottomSheetDialog(
            activity: Activity,
            onCreate: (name : String, amountInitial : String, amountPaid : String) -> Unit
        ) {
            val bottomSheetDialog = BottomSheetDialog(activity)
            val view = activity.layoutInflater.inflate(R.layout.dialog_create_debt, null)
            view.findViewById<ImageButton>(R.id.btnCloseDialog).setOnClickListener { bottomSheetDialog.dismiss() }
            val name = view.findViewById<EditText>(R.id.edtNameDebt)
            val amountInitial = view.findViewById<EditText>(R.id.edtAmountInitialDebt)
            val amountPaid = view.findViewById<EditText>(R.id.edtAmountPaidDebt)

            view.findViewById<Button>(R.id.btnCreate).setOnClickListener {
                if(name.text.trim().isEmpty() || amountPaid.text.trim().isEmpty()) return@setOnClickListener
                onCreate(name.text.trim().toString(),amountInitial.text.trim().toString(), amountPaid.text.trim().toString())
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }
    }
}