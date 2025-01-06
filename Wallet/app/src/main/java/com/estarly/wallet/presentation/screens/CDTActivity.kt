package com.estarly.wallet.presentation.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.estarly.wallet.databinding.ActivityCedetesBinding
import com.estarly.wallet.presentation.adapters.CDTAdapter
import com.estarly.wallet.presentation.dialogs.CreateCDTSheetDialog
import com.estarly.wallet.presentation.viewmodels.CDTViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CDTActivity : AppCompatActivity() {
    private val cdtViewModel : CDTViewModel by viewModels()
    private lateinit var binding : ActivityCedetesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCedetesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        getData()
        initObservers()
    }

    private fun initObservers() {
        with(binding){
            with(cdtViewModel){
                listCdts.observe(this@CDTActivity){
                    recyclerCedetes.adapter = CDTAdapter(it){
                        CreateCDTSheetDialog.showBottomSheetDialog(this@CDTActivity,it){amountInitial, tea, tna, time,rteFuente, image ->
                            cdtViewModel.createCdt(id = it.id,amount = amountInitial,tea = tea, tna = tna, time = time,rteFuente =rteFuente, image = image)
                        }
                    }
                }
                total.observe(this@CDTActivity){
                    txtTotalCdt.text = it
                }
            }
        }
    }

    private fun getData() {
        with(cdtViewModel){
            getCDTS()
        }
    }


    private fun initViews() {
        with(binding){
            btnBack.setOnClickListener { onBackPressed() }
            recyclerCedetes.layoutManager = LinearLayoutManager(this@CDTActivity, LinearLayoutManager.VERTICAL,false)

            btnCreateCDT.setOnClickListener {
                CreateCDTSheetDialog.showBottomSheetDialog(this@CDTActivity,){amountInitial, tea, tna, time,rteFuente, image ->
                    cdtViewModel.createCdt(id =null,amount = amountInitial,tea = tea, tna = tna, time = time,rteFuente = rteFuente, image = image)
                }
            }
        }
    }
}