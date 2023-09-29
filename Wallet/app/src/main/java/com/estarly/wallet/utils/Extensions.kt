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
fun Long.parseDateWithoutDay() : String{
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale("es", "ES"))
    val date = Date(this)
    return dateFormat.format(date)
}
fun Long.getMonth() : Int{
    val date = Date(this)
    val format = SimpleDateFormat("MM")
    val monthAsString = format.format(date)
    return monthAsString.toInt()
}
fun Long.getYear() : Int{
    val date = Date(this)
    val format = SimpleDateFormat("yyyy")
    val yearAsString = format.format(date)
    return yearAsString.toInt()
}
fun List<Double>.plusElements() : Double{
    var suma = 0.0
    for (item in this){ suma += item }
    return suma
}
fun Double.getPercentage(limit:Double) : Int = ((this/limit)*100).toInt()
fun String.removeLastCharacter() : String = this.dropLast(1)
