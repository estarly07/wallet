package com.estarly.wallet.data.datasources

import android.content.Context
import android.content.SharedPreferences

class WalletPreferences (
    val context: Context,
    val sharedPreferences : SharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
) {
    companion object{
        private const val PREFERENCES  = "PREFERENCES"
        private const val KEY_PASSWORD = "KEY_PASSWORD"
        private const val KEY_SALARY   = "KEY_SALARY"
        private const val KEY_VISIBILITY_SALARY = "KEY_VISIBILITY_SALARY"
    }
    var password: String?
        get() = sharedPreferences.getString(KEY_PASSWORD,"")
        set(value) {
            val editor = sharedPreferences.edit()
            editor.putString(KEY_PASSWORD, value)
            editor.apply()
        }
    var userSalary: Double
        get() = sharedPreferences.getFloat(KEY_SALARY,0f).toDouble()
        set(value) {
            val editor = sharedPreferences.edit()
            editor.putFloat(KEY_SALARY, value.toFloat())
            editor.apply()
        }
    var visibilitySalary: Boolean
        get() = sharedPreferences.getBoolean(KEY_VISIBILITY_SALARY,true)
        set(value) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(KEY_VISIBILITY_SALARY, value)
            editor.apply()
        }
}