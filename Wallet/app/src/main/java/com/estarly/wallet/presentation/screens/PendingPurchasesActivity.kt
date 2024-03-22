package com.estarly.wallet.presentation.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.estarly.wallet.databinding.ActivityPendingPurchasesBinding
import com.estarly.wallet.presentation.adapters.PendingPurchaseAdapter
import com.estarly.wallet.presentation.dialogs.CreatePendingPurchaseSheetDialog
import com.estarly.wallet.presentation.dialogs.showYesOrNoAlertDialog
import com.estarly.wallet.presentation.viewmodels.PendingPurchaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendingPurchasesActivity : AppCompatActivity() {
    private val pendingPurchaseViewModel : PendingPurchaseViewModel by viewModels()
    private lateinit var binding: ActivityPendingPurchasesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingPurchasesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initObservers()
        getData()
    }

    private fun initObservers() {
        with(pendingPurchaseViewModel){
            with(binding){
                pendingPurchases.observe(this@PendingPurchasesActivity){
                    recyclerPendingPurchases.adapter = PendingPurchaseAdapter(
                        it,
                        onUpdate = {pending->
                            CreatePendingPurchaseSheetDialog.showBottomSheetDialog(
                                this@PendingPurchasesActivity,pendingPurchaseModel = pending
                            ) { name, amount, image ->
                                pendingPurchaseViewModel.update(pending,name, amount, image)
                            }
                        },
                        onDelete = {pending->
                            showYesOrNoAlertDialog(
                                this@PendingPurchasesActivity,
                                title = "¿Estas seguro?",
                                description = "Quieres eliminar la compra pendiente '${pending.name}'",
                                onPositive = {pendingPurchaseViewModel.delete(pending) }
                            )
                        },
                        onFinishedPurchase = {pending, isChecked ->
                            pendingPurchaseViewModel.updateFinishedPurchase(pending,isChecked)

                        },
                    )
                }
                amountTotalPendingPurchases.observe(this@PendingPurchasesActivity){
                    txtAmountTotalPendingPurchases.text = it
                }
            }
        }
    }

    private fun getData() {
        with(pendingPurchaseViewModel){
            getPendingPurchases()
        }
    }

    private fun initViews() {
        with(binding){
            recyclerPendingPurchases.layoutManager = GridLayoutManager(this@PendingPurchasesActivity,2)
            btnCreatePurchase.setOnClickListener {
                CreatePendingPurchaseSheetDialog.showBottomSheetDialog(
                    this@PendingPurchasesActivity
                ) { name, amount, image ->
                    pendingPurchaseViewModel.createPendingPurchase(name, amount, image)
                }
            }
            btnBack.setOnClickListener { onBackPressed() }
        }
    }
}