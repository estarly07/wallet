package com.estarly.wallet.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Double.formatSalary(): String {
    val format = NumberFormat.getInstance(Locale("es", "ES")) as DecimalFormat
    format.applyPattern("#,###,###,##0.00")
    if (this % 1 == 0.0) {
        format.applyPattern("#,###,###,###")
    } else {
        format.applyPattern("#,###,###,##0.00")
    }
    return format.format(this)
}
fun Long.parseDate() : String{
    val dateFormat = SimpleDateFormat("h:mm a - MMM dd, yyyy", Locale("es", "ES"))
    val date = Date(this)
    return dateFormat.format(date)
}
fun Long.getMonth() : Int{
    val date = Date(this)
    val format = SimpleDateFormat("MM")
    val mesComoString = format.format(date)
    return mesComoString.toInt()
}