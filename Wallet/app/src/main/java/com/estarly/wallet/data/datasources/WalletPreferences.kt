package com.estarly.wallet.data.datasources

import android.content.Context
import android.content.SharedPreferences

class WalletPreferences (
    val context: Context,
    val sharedPreferences : SharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
) {
    companion object{
        private const val PREFERENCES = "PREFERENCES"
        private const val KEY_PASSWORD = "KEY_PASSWORD"
    }
    var password: String?
        get() = sharedPreferences.getString(KEY_PASSWORD,"")
        set(value) {
            val editor = sharedPreferences.edit()
            editor.putString(KEY_PASSWORD, value)
            editor.apply()
        }
}