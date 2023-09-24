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
import com.estarly.wallet.presentation.adapters.DistributionSelectAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.makeramen.roundedimageview.RoundedImageView

class CreateGoalSheetDialog {
    companion object {
        @SuppressLint("MissingInflatedId")
        fun showBottomSheetDialog(
            activity : Activity,
            onCreate : (
                name:String,
                amountInitial : String?,
                AmountTotal:String,
                image:String,
                description:String) -> Unit
        ) {

            val bottomSheetDialog = BottomSheetDialog(activity)
            val view = activity.layoutInflater.inflate(R.layout.dialog_create_goal, null)
            view.findViewById<ImageButton>(R.id.btnCloseDialog).setOnClickListener { bottomSheetDialog.dismiss() }
            val edtImageGoal = view.findViewById<EditText>(R.id.edtImageGoal)
            val edtName = view.findViewById<EditText>(R.id.edtNameGoal)
            val edtAmountInitial = view.findViewById<EditText>(R.id.edtAmountInitialGoal)
            val edtAmountTotal = view.findViewById<EditText>(R.id.edtAmountTotalGoal)
            val edtDescription = view.findViewById<EditText>(R.id.edtDescriptionGoal)
            val imageGoal = view.findViewById<ImageView>(R.id.imgGoalCreate)
            val btnCreate = view.findViewById<Button>(R.id.btnCreate)

            edtImageGoal.addTextChangedListener {
                Glide.with(view)
                    .load(it.toString())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.no_found)
                    .into(imageGoal)
            }
            btnCreate.setOnClickListener {
                edtImageGoal.error = if(edtImageGoal.text.isEmpty()) "Dato obligatorio" else null
                edtName.error = if(edtName.text.isEmpty()) "Dato obligatorio" else null
                edtAmountTotal.error = if(edtAmountTotal.text.isEmpty()) "Dato obligatorio" else null

                if(edtImageGoal.error!=null || edtName.error!=null || edtAmountTotal.error!=null) return@setOnClickListener
                onCreate(
                    edtName.text.toString(),
                    edtAmountInitial.text.toString().ifEmpty { null },
                    edtAmountTotal.text.toString(),
                    edtImageGoal.text.toString(),
                    edtDescription.text.toString(),
                )
                bottomSheetDialog.dismiss()
                Log.i("ASDASD","asdasdasdas")
            }

            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }
    }
}