package com.estarly.wallet.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.estarly.wallet.databinding.ActivityKeyboardBinding
import com.estarly.wallet.presentation.viewmodels.KeyboardViewModel
import com.estarly.wallet.utils.formatSalary

class KeyboardActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding : ActivityKeyboardBinding
    private val keyboardViewModel : KeyboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKeyboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initObservers()
    }

    @SuppressLint("SetTextI18n")
    private fun initObservers() {
        with(keyboardViewModel){
            with(binding){
                amount.observe(this@KeyboardActivity){
                    keyboard.buttonZero.isEnabled = it.isNotEmpty()
                    txtAmount.text =  "$ ${if(it.isEmpty()) "0" else it.toDouble().formatSalary()}"
                }
            }
        }
    }

    private fun initView() {
        with(binding){
            with(keyboard){
                val rows = listOf(row1, row2, row3)

                val tags = listOf("1,2,3", "4,5,6", "7,8,9")
                rows.forEachIndexed { index, row ->
                    val tag = tags[index].split(",")
                    row.btnOne.text = tag[0]
                    row.btnOne.setOnClickListener(this@KeyboardActivity)

                    row.btnTwo.text = tag[1]
                    row.btnTwo.setOnClickListener(this@KeyboardActivity)

                    row.btnThree.text = tag[2]
                    row.btnThree.setOnClickListener(this@KeyboardActivity)
                }
                buttonZero.isEnabled = false
                buttonZero.setOnClickListener(this@KeyboardActivity)
                buttonBackspace.setOnClickListener { keyboardViewModel.deleteLastCharacter()}
            }
            btnDone.setOnClickListener { Log.v("HOLI","${keyboardViewModel.amount.value}") }
        }
    }

    override fun onClick(v: View?) {
        val view = v as Button? ?: return
        keyboardViewModel.appendCharacter(view.text.toString())
    }
}