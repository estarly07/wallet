package com.estarly.wallet.presentation.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.estarly.wallet.databinding.ActivityCreationDistributionBinding
import com.estarly.wallet.presentation.dialogs.BackgroundsBottomSheetDialog
import com.estarly.wallet.presentation.viewmodels.CreationDistributionViewModel
import com.estarly.wallet.utils.formatSalary
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreationDistributionActivity : AppCompatActivity() {
    lateinit var binding : ActivityCreationDistributionBinding
    private val creationDistributionViewModel : CreationDistributionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreationDistributionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initObservers()
        val id = intent.getIntExtra("idDistribution",-1)
        if(id != -1) creationDistributionViewModel.getDistribution(id)
    }

    private fun initObservers() {
        with(binding){
            with(creationDistributionViewModel){
                changeBackgroundDistribution.observe(this@CreationDistributionActivity){
                    imgBackgroundCreationDistribution.setImageResource(it)
                }
                closeActivity.observe(this@CreationDistributionActivity){
                    onBackPressed()
                }
                errorAmount.observe(this@CreationDistributionActivity){
                    edtAmountCreationDistribution.error = it
                }
                distributionUpdate.observe(this@CreationDistributionActivity){
                    edtAmountCreationDistribution.setText(it?.amountSaved?.toString())
                    edtNameCreationDistribution.setText(it?.name)
                    changeBackgroundDistribution(it.image)
                }
            }
        }
    }

    private fun initViews() {
        with(binding){
            btnBackDistribution.setOnClickListener { onBackPressed() }
            edtAmountCreationDistribution.addTextChangedListener {
                txtAmountCreationDistribution.text = it.toString()
            }
            edtNameCreationDistribution.addTextChangedListener {
                txtNameCreationDistribution.text = it.toString()
            }
            imgBackgroundCreationDistribution.setOnClickListener {
                BackgroundsBottomSheetDialog.showBottomSheetDialog(this@CreationDistributionActivity){
                    creationDistributionViewModel.changeBackgroundDistribution(it)
                }
            }
            btnCreateDistribution.setOnClickListener {
                creationDistributionViewModel.saveDistribution(
                    edtNameCreationDistribution.text.toString(),
                    edtAmountCreationDistribution.text.toString(),
                )
            }
            edtAmountCreationDistribution.addTextChangedListener {
                creationDistributionViewModel.changeAmount(it.toString())
            }

        }
    }
}