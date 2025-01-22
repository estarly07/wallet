package com.estarly.wallet.presentation.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.estarly.wallet.databinding.ActivityTransactionsBinding
import com.estarly.wallet.presentation.adapters.TransactionsAdapter
import com.estarly.wallet.presentation.viewmodels.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTransactionsBinding
    private val transactionViewModel: TransactionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initObservers()
        getData()
    }

    private fun getData() {
        with(transactionViewModel){
            getTransactions()
        }
    }

    private fun initObservers() {
        with(binding){
            with(transactionViewModel){
                listTransactions.observe(this@TransactionsActivity){
                    recyclerAllTransactions.adapter = TransactionsAdapter(it){
                        DetailTransactionActivity.transaction = it
                        startActivity(Intent(this@TransactionsActivity, DetailTransactionActivity::class.java))
                    }
                }
            }
        }
    }

    private fun initViews() {
        with(binding){
            btnBackTransaction.setOnClickListener { onBackPressed() }
            recyclerAllTransactions.layoutManager = LinearLayoutManager(this@TransactionsActivity,LinearLayoutManager.VERTICAL,false)
        }
    }
}