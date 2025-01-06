package com.estarly.wallet.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Double.getProfit(
    tea       : Double,//Tasa efectiva anual
    tna       : Double,//Tasa nominal anual
    days      : Int,   //plazo
    rteFuente : Double?//Retención en la fuente
) : String {
    val newTea = tea /100
    // Calcular la tasa efectiva para el período según TEA o TNA
    val ratePeriod =/* if (newTea > 0) {
        (1 + newTea).pow(days / 365.0) - 1 // Si se usa TEA, la fórmula de capitalización compuesta
    } else {*/
        (tna / 100) * (days / 360.0)  // Si no se usa TEA, usamos TNA de forma lineal
    //}

    val grossInterest = this * ratePeriod// Calcular intereses brutos generados
    return (grossInterest - (rteFuente ?: 0.0)).formatSalary()// Calcular intereses netos
}

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
fun Long.isThisDateInCurrentMonth(): Boolean {
    val calendar = Calendar.getInstance()
    val currentMonth = calendar.get(Calendar.MONTH)
    calendar.timeInMillis = this
    return calendar.get(Calendar.MONTH) == currentMonth
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
fun Double.doubleToStringWithoutDecimalIfZero(): String
    = if (this % 1.0 == 0.0) { this.toLong().toString() }
      else { this.toString() }
