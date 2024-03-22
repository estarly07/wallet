package com.estarly.wallet.presentation.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.estarly.wallet.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class DepositSalaryBottomSheetDialog {
    companion object {
        @SuppressLint("MissingInflatedId")
        fun showBottomSheetDialog(
            activity   : Activity,
            salaryUser : Double,
            onDismiss  : () -> Unit,
            onDepositMoney: (String) -> Unit,
        ) {
            val bottomSheetDialog = BottomSheetDialog(activity)
            val view = activity.layoutInflater.inflate(R.layout.dialog_to_deposit_salary, null)
            val amount = view.findViewById<EditText>(R.id.edtAmount)
            val btnDepositMoney = view.findViewById<Button>(R.id.btnDepositMoney)
            view.findViewById<ImageButton>(R.id.btnCloseDialog).setOnClickListener { bottomSheetDialog.dismiss() }
            if(salaryUser > 0) { amount.setText(salaryUser.toString())}
            btnDepositMoney.setOnClickListener {
                if(amount.error != null || amount.text.toString().isEmpty()) return@setOnClickListener
                onDepositMoney(amount.text.toString())
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.setOnDismissListener {onDismiss()}
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }
    }
}