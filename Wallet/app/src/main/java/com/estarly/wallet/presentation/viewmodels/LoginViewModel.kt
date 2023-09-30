package com.estarly.wallet.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.estarly.wallet.domain.usescases.CreatePasswordUseCase
import com.estarly.wallet.domain.usescases.HavePasswordUseCase
import com.estarly.wallet.domain.usescases.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val havePasswordUseCase: HavePasswordUseCase,
    private val createPasswordUseCase: CreatePasswordUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel(){
    private val _havePassword = MutableLiveData<Boolean>()
    val havePassword : LiveData<Boolean> = _havePassword
    private val _goToHome = MutableLiveData<Boolean>()
    val goToHome : LiveData<Boolean> = _goToHome
    private val _showAlertCreatePassword = MutableLiveData<Boolean>()
    val showAlertCreatePassword : LiveData<Boolean> = _showAlertCreatePassword

    fun havePassword(){
        _havePassword.value = havePasswordUseCase()
    }
    fun validatePassword(password: String) {
        if(password.length != 4) return
        if(_havePassword.value == false){
            _showAlertCreatePassword.value = true
        }else{
            if(validatePasswordUseCase(password)){
                _goToHome.value = true
            }else{
                //TODO:mostrar algo cuando no es valida
            }

        }

    }
    fun createPassword(password: String){
        createPasswordUseCase(password)
        goHome()
    }

    fun cancelDialogCreatePassword() {
        _showAlertCreatePassword.value = false
    }

    fun goHome() {
        _goToHome.value = true
    }
}