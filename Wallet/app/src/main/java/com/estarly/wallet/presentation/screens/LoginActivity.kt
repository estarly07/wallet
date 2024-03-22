package com.estarly.wallet.presentation.screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.allViews
import com.estarly.wallet.databinding.ActivityLoginBinding
import com.estarly.wallet.presentation.dialogs.showYesOrNoAlertDialog
import com.estarly.wallet.presentation.viewmodels.LoginViewModel
import com.estarly.wallet.utils.removeLastCharacter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val loginViewModel : LoginViewModel by viewModels()
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initObservers()
        getData()
    }

    private fun getData() {
        with(loginViewModel){
            havePassword()
        }
    }

    private fun initObservers() {
        with(loginViewModel){
            with(binding){
                havePassword.observe(this@LoginActivity){
                    if(it) return@observe
                    keyboardLogin.btnBiometric.visibility = View.GONE
                    txtDescriptionLogin.text ="¡Hora de crear tu contraseña de 4 dígitos!\nAsegúrate de que sea segura y que no la olvides fácilmente."
                    txtTitleLogin.text ="Registro"
                }
                goToHome.observe(this@LoginActivity){
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
                showAlertCreatePassword.observe(this@LoginActivity){
                    if(!it) return@observe
                    showYesOrNoAlertDialog(
                        this@LoginActivity,
                        title = "¿Estas seguro?",
                        description = "¿Listo para darle vida a esta nueva contraseña? \uD83D\uDE80 \n¡Confirma que es la elegida",
                        onPositive = {loginViewModel.createPassword(edtPassword.text.toString().trim())},
                        onCancel = { loginViewModel.cancelDialogCreatePassword() }
                    )
                }
            }
        }
    }

    private fun initViews() {
        with(binding){
            keyboardLogin.root.allViews.iterator().forEach { view ->
                if(view is GridLayout) return@forEach
                view.setOnClickListener {
                    if(view is TextView) {
                        edtPassword.setText( "${edtPassword.text}${view.tag}")
                    }
                    else if(view is ImageButton) {
                        if(view.tag == "ic_finger_biometric"){
                            showBiometrics()
                        }else{
                            if(edtPassword.text.isNotEmpty())
                                edtPassword.setText(edtPassword.text.toString().removeLastCharacter())
                        }
                    }
                    setPasswordLayout()

                }
            }
        }
    }

    private fun setPasswordLayout() {
        with(binding){
            val chars = edtPassword.text.toString().toCharArray()
            edtPasswordLogin.txtNumOnePassword.text   = if(chars.isNotEmpty()) chars[0].toString() else " "
            edtPasswordLogin.txtNumTwoPassword.text   = if(chars.size > 1) chars[1].toString() else " "
            edtPasswordLogin.txtNumThreePassword.text = if(chars.size > 2) chars[2].toString() else " "
            edtPasswordLogin.txtNumFourPassword.text  = if(chars.size > 3) chars[3].toString() else " "
            loginViewModel.validatePassword(edtPassword.text.toString().trim())
        }
    }
    private fun showBiometrics(){
        // En el método onCreate de tu actividad o fragmento
        val biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación biométrica")
            .setSubtitle("¡Inicia sesión de forma sencilla y segura con tu huella digital! tu huella te abrirán las puertas a tu cuenta.")
            .setNegativeButtonText("Cancelar")
            .build()

        val biometricPrompt = BiometricPrompt(
            this,
            ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    loginViewModel.goHome()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    // Maneja fallos de autenticación biométrica aquí
                }
            }
        )
        biometricPrompt.authenticate(biometricPromptInfo)
    }


}