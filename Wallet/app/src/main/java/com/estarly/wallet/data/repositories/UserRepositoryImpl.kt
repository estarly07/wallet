package com.estarly.wallet.data.repositories

import android.util.Base64
import android.util.Log
import com.estarly.wallet.data.datasources.WalletPreferences
import com.estarly.wallet.domain.repositories.UserRepository
import java.security.MessageDigest

class UserRepositoryImpl(
    private val walletPreferences: WalletPreferences
) : UserRepository {
    override fun createPassword(password: String) {
        val encryptedPassword = hashPassword(password)
        Log.i("createPassword","$password $encryptedPassword")
        walletPreferences.password = encryptedPassword
    }

    override fun validatePassword(password: String) : Boolean {
        val inputPasswordHash = hashPassword(password).trim()
        Log.i("validatePassword","$password $inputPasswordHash ${walletPreferences.password?.trim()}")
        Log.i("validatePassword","${inputPasswordHash == walletPreferences.password?.trim()}")
        return inputPasswordHash == walletPreferences.password?.trim()
    }

    override fun havePassword(): Boolean = walletPreferences.password?.isNotEmpty() ?: false

    private fun hashPassword(password: String): String {
        val bytes = password.toByteArray(Charsets.UTF_8)
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return Base64.encodeToString(digest, Base64.DEFAULT)
    }
}