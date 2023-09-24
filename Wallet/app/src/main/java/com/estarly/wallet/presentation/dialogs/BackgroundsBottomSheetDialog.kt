package com.estarly.wallet.presentation.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estarly.wallet.R
import com.estarly.wallet.presentation.adapters.BackgroundsAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class BackgroundsBottomSheetDialog {
    companion object {
        private var backgroundSelect : Int? = null
        @SuppressLint("MissingInflatedId")
        fun showBottomSheetDialog(activity: Activity, onConfirm : (Int) -> Unit) {
            val bottomSheetDialog = BottomSheetDialog(activity)
            val view = activity.layoutInflater.inflate(R.layout.backgrounds_bottom_sheet_dialog, null)
            view.findViewById<ImageButton>(R.id.btnCloseDialog).setOnClickListener { bottomSheetDialog.dismiss() }
            view.findViewById<Button>(R.id.btnConfirm).setOnClickListener {
                backgroundSelect?.let { onConfirm(it) }
                backgroundSelect = null
                bottomSheetDialog.dismiss()
            }
            val recycler = view.findViewById<RecyclerView>(R.id.recyclerBackgrounds)
            recycler.layoutManager = GridLayoutManager(activity,3)
            recycler.adapter =BackgroundsAdapter(
                listOf(
                    R.drawable.one,
                    R.drawable.two,
                    R.drawable.three,
                    R.drawable.four,
                    R.drawable.five,
                    R.drawable.six,
                )
            ) { backgroundSelect = it }
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }
    }
}