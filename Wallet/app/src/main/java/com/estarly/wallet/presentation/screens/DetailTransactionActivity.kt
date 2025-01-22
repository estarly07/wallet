package com.estarly.wallet.presentation.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.estarly.wallet.R
import com.estarly.wallet.databinding.ActivityDetailTransactionBinding
import com.estarly.wallet.domain.models.TransactionModel
import com.estarly.wallet.utils.formatSalary
import com.estarly.wallet.utils.parseDateWithoutDay
import com.estarly.wallet.utils.setBackgroundStatus

class DetailTransactionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailTransactionBinding
    companion object{
        var transaction : TransactionModel? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityDetailTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBackgroundStatus(R.color.backgroundHeader)
        initView()
    }

    private fun initView() {
        with(binding){
            txtDate.text = transaction!!.date.parseDateWithoutDay()
            txtType.text = transaction!!.typeTransaction.type
            txtId.text   = transaction!!.id.toString()
            txtAmount.text = "$ ${transaction!!.amount.formatSalary()}"
            btnDone.setOnClickListener {
                transaction = null
                finish()
            }
        }
    }

    override fun onBackPressed() {
        transaction = null
        super.onBackPressed()
    }
}