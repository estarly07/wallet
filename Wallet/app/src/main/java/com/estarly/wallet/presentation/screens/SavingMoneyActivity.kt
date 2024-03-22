package com.estarly.wallet.presentation.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.estarly.wallet.databinding.ActivitySavingMoneyBinding
import com.estarly.wallet.presentation.dialogs.SavingOrTakeOffMoneyBottomSheetDialog
import com.estarly.wallet.presentation.viewmodels.SavingMoneyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavingMoneyActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySavingMoneyBinding
    private val savingMoneyViewModel : SavingMoneyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavingMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initObservers()
        getData()
    }

    private fun getData() {
        with(savingMoneyViewModel){
            getAmountSaving()
        }
    }

    private fun initObservers() {
        with(binding){
            with(savingMoneyViewModel){
                amountSaving.observe(this@SavingMoneyActivity){
                    txtAmountSaving.text = it
                }
            }
        }
    }

    private fun initViews() {
        with(binding){
            btnBack.setOnClickListener { onBackPressed() }
            btnTakeOffMoney.setOnClickListener {
                SavingOrTakeOffMoneyBottomSheetDialog.showBottomSheetDialog(
                    this@SavingMoneyActivity,
                    savingMoneyViewModel.amountAvailable,
                    onSavingMoney = { savingMoneyViewModel.retireMoney(it) },
                    onDismiss = {},
                    takeOfMoney = true
                )
            }
        }
    }
}