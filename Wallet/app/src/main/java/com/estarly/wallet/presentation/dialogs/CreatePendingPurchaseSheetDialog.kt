package com.estarly.wallet.presentation.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.estarly.wallet.R
import com.estarly.wallet.domain.models.CDTModel
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.PendingPurchaseModel
import com.estarly.wallet.presentation.adapters.DistributionSelectAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.makeramen.roundedimageview.RoundedImageView

class CreatePendingPurchaseSheetDialog {
    companion object {
        @SuppressLint("MissingInflatedId")
        fun showBottomSheetDialog(
            activity : Activity,
            pendingPurchaseModel: PendingPurchaseModel? = null,
            onCreate : (
                name:String,
                amount : String,
                image:String) -> Unit
        ) {

            val bottomSheetDialog = BottomSheetDialog(activity)
            val view = activity.layoutInflater.inflate(R.layout.dialog_create_pending_purchase, null)
            view.findViewById<ImageButton>(R.id.btnCloseDialog).setOnClickListener { bottomSheetDialog.dismiss() }
            val edtImageGoal = view.findViewById<EditText>(R.id.edtImagePurchase)
            val edtName = view.findViewById<EditText>(R.id.edtNamePurchase)
            val edtAmountTotal = view.findViewById<EditText>(R.id.edtAmountPurchase)
            val imageGoal = view.findViewById<ImageView>(R.id.imgPurchaseCreate)
            val btnCreate = view.findViewById<Button>(R.id.btnCreate)

            edtImageGoal.addTextChangedListener {
                Glide.with(view)
                    .load(it.toString())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.no_found)
                    .into(imageGoal)
            }
            pendingPurchaseModel?.let {
                edtAmountTotal.setText("${it.amount}")
                edtImageGoal.setText(it.image)
                edtName.setText(it.name)
                btnCreate.text = "Actualizar"
            }
            btnCreate.setOnClickListener {
                edtImageGoal.error = if(edtImageGoal.text.isEmpty()) "Dato obligatorio" else null
                edtName.error = if(edtName.text.isEmpty()) "Dato obligatorio" else null
                edtAmountTotal.error = if(edtAmountTotal.text.isEmpty()) "Dato obligatorio" else null

                if(edtImageGoal.error!=null || edtName.error!=null || edtAmountTotal.error!=null) return@setOnClickListener
                onCreate(
                    edtName.text.toString(),
                    edtAmountTotal.text.toString(),
                    edtImageGoal.text.toString(),
                )
                bottomSheetDialog.dismiss()
                Log.i("ASDASD","asdasdasdas")
            }

            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }
    }
}