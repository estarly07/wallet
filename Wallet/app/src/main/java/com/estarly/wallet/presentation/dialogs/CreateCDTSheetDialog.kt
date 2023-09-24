package com.estarly.wallet.presentation.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estarly.wallet.R
import com.estarly.wallet.domain.models.CDTModel
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.presentation.adapters.DistributionSelectAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.makeramen.roundedimageview.RoundedImageView

class CreateCDTSheetDialog {
    companion object {
        var imageSelect = R.drawable.one
        @SuppressLint("MissingInflatedId")
        fun showBottomSheetDialog(
            activity: Activity,
            cdt: CDTModel? = null,
            onCreate: (amountInitial : String, image : Int) -> Unit
        ) {

            val bottomSheetDialog = BottomSheetDialog(activity)
            val view = activity.layoutInflater.inflate(R.layout.dialog_create_cdt, null)
            view.findViewById<ImageButton>(R.id.btnCloseDialog).setOnClickListener { bottomSheetDialog.dismiss() }
            val amountInitial = view.findViewById<EditText>(R.id.edtAmountInitialDebt)
            val image = view.findViewById<RoundedImageView>(R.id.imgBackgroundCDT)
            if(cdt !=null){
                imageSelect = cdt.image
                image.setImageResource(imageSelect)
                amountInitial.setText(cdt.amount.toString())
                view.findViewById<Button>(R.id.btnCreate).text = "Actualizar"
            }


            view.findViewById<Button>(R.id.btnCreate).setOnClickListener {
                if(amountInitial.text.trim().isEmpty()) return@setOnClickListener
                onCreate(amountInitial.text.trim().toString(), imageSelect)
                bottomSheetDialog.dismiss()
            }
            image.setOnClickListener {
                BackgroundsBottomSheetDialog.showBottomSheetDialog(activity){
                    imageSelect = it
                    image.setImageResource(imageSelect)
                }
            }

            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }
    }
}