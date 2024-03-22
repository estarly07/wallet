package com.estarly.wallet.presentation.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.estarly.wallet.databinding.ActivityCreationAutomationDistributionBinding
import com.estarly.wallet.presentation.adapters.DistributionAutomationAdapter
import com.estarly.wallet.presentation.viewmodels.CreationAutomationDistributionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreationAutomationDistributionActivity : AppCompatActivity() {
    private val creationAutomationDistributionViewModel : CreationAutomationDistributionViewModel by viewModels()
    private lateinit var binding : ActivityCreationAutomationDistributionBinding
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
            getDistributions()
        }
    }

    private fun initObservers() {
        with(creationAutomationDistributionViewModel) {
            with(binding) {
                listDistributionOfMoney.observe(this@CreationAutomationDistributionActivity){
                    recyclerDistributions.adapter = DistributionAutomationAdapter(
                        it
                    ) { distribution, position, amount ->
                        updateExpectedAmountDistribution(distribution,amount)
                    }
                }
            }
        }
    }

    private fun initViews() {
        with(binding){
            btnBackDistribution.setOnClickListener { onBackPressed() }
            recyclerDistributions.layoutManager = LinearLayoutManager(this@CreationAutomationDistributionActivity,LinearLayoutManager.VERTICAL,false)
        }
    }
}