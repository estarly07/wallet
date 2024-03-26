package com.estarly.wallet.presentation.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.estarly.wallet.databinding.ActivityDebtsBinding
import com.estarly.wallet.presentation.adapters.DebtsAdapter
import com.estarly.wallet.presentation.dialogs.CreateDebtSheetDialog
import com.estarly.wallet.presentation.dialogs.showYesOrNoAlertDialog
import com.estarly.wallet.presentation.viewmodels.DebtViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DebtsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDebtsBinding
    private val debtViewModel : DebtViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDebtsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initObservers()
        getData()
    }

    private fun getData() {
        with(debtViewModel){
            getDebts()
        }
    }

    private fun initObservers() {
        with(debtViewModel){
            with(binding){
                listDebts.observe(this@DebtsActivity){
                    recyclerDebts.adapter = DebtsAdapter(it){debt ->
                        showYesOrNoAlertDialog(
                            this@DebtsActivity,
                            title = "¿Estas seguro?",
                            description = "Quieres eliminar esta deuda '${debt.name}'",
                            onPositive = { debtViewModel.deleteDebt(debt) }
                        )
                    }
                }
            }
        }
    }

    private fun initViews() {
        with(binding){
            btnBack.setOnClickListener { onBackPressed() }
            recyclerDebts.layoutManager = LinearLayoutManager(this@DebtsActivity, LinearLayoutManager.VERTICAL,false)
            btnCreateDebt.setOnClickListener {
                CreateDebtSheetDialog.showBottomSheetDialog(
                    this@DebtsActivity,
                    onCreate = { name, amountInitial, amountPaid->
                        debtViewModel.saveDebt(name,amountInitial,amountPaid)
                    }
                )
            }
        }
    }
}