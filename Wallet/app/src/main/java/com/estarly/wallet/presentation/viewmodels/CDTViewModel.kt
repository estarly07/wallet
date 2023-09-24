package com.estarly.wallet.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estarly.wallet.R
import com.estarly.wallet.domain.models.CDTModel
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.usescases.CreateCdtUseCase
import com.estarly.wallet.domain.usescases.GetAllCDTSUseCase
import com.estarly.wallet.domain.usescases.GetAmountSalaryUseCase
import com.estarly.wallet.domain.usescases.GetDistributionUseCase
import com.estarly.wallet.domain.usescases.GetRemainingMoneyUseCase
import com.estarly.wallet.domain.usescases.SaveDistributionUseCase
import com.estarly.wallet.domain.usescases.UpdateCdtUseCase
import com.estarly.wallet.domain.usescases.UpdateDistributionOfMoneyUseCase
import com.estarly.wallet.utils.formatSalary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CDTViewModel @Inject constructor(
    private val getAllCDTSUseCase: GetAllCDTSUseCase,
    private val updateCdtUseCase: UpdateCdtUseCase,
    private val createCdtUseCase: CreateCdtUseCase
) : ViewModel(){
    private val _listCdts = MutableLiveData<List<CDTModel>>()
    val listCdts : LiveData<List<CDTModel>> = _listCdts

    fun getCDTS(){
        viewModelScope.launch {
            getAllCDTSUseCase()
                .map { it ?: listOf() }
                .collect{ _listCdts.value = it }
        }
    }

    fun createCdt(id : Int?, amount: String, image : Int){
        viewModelScope.launch {
            if(id==null){
                createCdtUseCase(amount,image)
            }else{
                updateCdtUseCase(id,amount,image)
            }
        }
    }
}