package com.estarly.wallet.presentation.dialogs

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import com.estarly.wallet.R

/**
 * Show an alert with two options yes or cancel
 * @param title is a String for the alert title
 * @param description is a String for the alert message
 * @param onPositive when the positive button is touched
 * @param onCancel when the cancel button is touched
 * */
fun showYesOrNoAlertDialog(
    context     : Context,
    title       : String,
    description : String,
    onPositive  : () -> Unit = {},
    onCancel    : () -> Unit = {},
) {
    val alertDialog = AlertDialog.Builder(context)
    alertDialog.apply {
        setTitle(title)
        setMessage(description)
        setPositiveButton("Si") { _: DialogInterface?, _: Int -> onPositive() }
        setNegativeButton("Cancelar") { _, _ -> onCancel() }
    }.create().show()
}