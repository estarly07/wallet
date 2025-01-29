package com.estarly.wallet.presentation.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.estarly.wallet.databinding.ActivityCreationAutomationDistributionBinding
import com.estarly.wallet.presentation.adapters.DistributionAutomationAdapter
import com.estarly.wallet.presentation.viewmodels.CreationAutomationDistributionViewModel
import com.estarly.wallet.utils.formatSalary
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class CreationAutomationDistributionActivity : AppCompatActivity() {
    private val creationAutomationDistributionViewModel : CreationAutomationDistributionViewModel by viewModels()
    private lateinit var binding : ActivityCreationAutomationDistributionBinding
    private var distributionAdapter : DistributionAutomationAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreationAutomationDistributionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initObservers()
        getData()
    }

    private fun getData() {
        with(creationAutomationDistributionViewModel){
            getAvailableAmount()
            getDistributions()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initObservers() {
        with(creationAutomationDistributionViewModel) {
            with(binding) {
                listDistributionOfMoney.observe(this@CreationAutomationDistributionActivity){
                    distributionAdapter = DistributionAutomationAdapter(list = it) { distribution, position, amount ->
                        txtRequiredSalary.text = "$ ${distributionAdapter?.getRequiredSalaryFormatted() ?: 0}"
                        calculateProgress(amountAvailable.value!!)
                        updateExpectedAmountDistribution(distribution,amount)
                    }
                    txtRequiredSalary.text = "$ ${distributionAdapter?.getRequiredSalaryFormatted() ?: 0}"
                    calculateProgress(amountAvailable.value!!)
                    recyclerDistributions.adapter = distributionAdapter
                }
                amountAvailable.observe(this@CreationAutomationDistributionActivity){
                    txtAvailableSalary.text = it.formatSalary()
                    calculateProgress(it)
                }
            }
        }
    }

    private fun calculateProgress(amountAvailable : Double) {
        distributionAdapter?.let{
            with(binding){
                val percentage = ((amountAvailable * 100)/ distributionAdapter!!.getRequiredSalary()).roundToInt()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    progress.setProgress(percentage, true)
                }else{
                    progress.setProgress(percentage)
                }
            }
        }
    }

    private fun initViews() {
        with(binding){
            btnBackDistribution.root.setOnClickListener { onBackPressed() }
            recyclerDistributions.layoutManager = LinearLayoutManager(this@CreationAutomationDistributionActivity,LinearLayoutManager.VERTICAL,false)
            btnDistribute.setOnClickListener { creationAutomationDistributionViewModel.distribute() }
            btnEdit.setOnClickListener {
                distributionAdapter?.let {
                    distributionAdapter!!.editFields()
                    btnEdit.text =  if(distributionAdapter!!.editFields) "Refrescar" else "Editar"
                }
                scroll.smoothScrollTo(0,0)
            }
            recyclerDistributions.isNestedScrollingEnabled = false
        }
    }
}