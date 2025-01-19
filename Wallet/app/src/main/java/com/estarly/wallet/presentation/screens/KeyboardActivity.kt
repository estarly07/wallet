package com.estarly.wallet.presentation.screens

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.estarly.wallet.R
import com.estarly.wallet.databinding.ActivityKeyboardBinding
import com.estarly.wallet.domain.models.DebtModel
import com.estarly.wallet.domain.models.DistributionOfMoneyModel
import com.estarly.wallet.domain.models.TypeTransactions
import com.estarly.wallet.domain.models.advice
import com.estarly.wallet.domain.models.name
import com.estarly.wallet.presentation.adapters.DebtsSelectAdapter
import com.estarly.wallet.presentation.adapters.DistributionSelectAdapter
import com.estarly.wallet.presentation.viewmodels.KeyboardViewModel
import com.estarly.wallet.utils.formatSalary
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KeyboardActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding : ActivityKeyboardBinding
    private val keyboardViewModel : KeyboardViewModel by viewModels()
    private lateinit var typeTransaction : TypeTransactions
    private val debtAdapter =  DebtsSelectAdapter{ debt ->
        keyboardViewModel.selectDebt(debt)
        collapseRecyclerView(binding.expandableContainerDebts)
    }
    companion object{
        const val KEY_ARGUMENT ="KEY_ARGUMENT"
        var onFinished : ((amount : String, distribution : DistributionOfMoneyModel?, debt : DebtModel?) -> Unit)? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKeyboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val type = intent.extras?.getString(KEY_ARGUMENT) ?: return finish()
        typeTransaction = TypeTransactions.valueOf(type)
        keyboardViewModel.setTypeTransaction(typeTransaction)
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
                validAmount.observe(this@KeyboardActivity){
                    btnDone.isEnabled = it
                    btnDone.setBackgroundColor(if(it) getColor(R.color.black) else getColor(R.color.grayDark))
                    txtAmount.setTextColor(if(it || amount.value.isNullOrEmpty())getColor(R.color.black) else getColor(R.color.percentagePays))
                }
                distributionSelect.observe(this@KeyboardActivity){
                    with(btnSelectDistribution){
                        txtTitleItemDistribution.text = it.name
                        txtAmountItemDistribution.text = it.amountSaved.formatSalary()
                        imgItemDistribution.setImageResource(it.image)
                    }
                }
                debtSelect.observe(this@KeyboardActivity){debt ->
                    with(btnSelectDebt){
                        txtNameDebtItem.text = debt.name
                        txtMissingAmountDebtItem.text = debt.missingAmount.formatSalary()
                        txtDateDebtItem.text = debt.dateLastPaidFormatted
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            progressDebtItem.setProgress(debt.percentagePaid,true)
                        }
                        debtAdapter.selectDebt(debt)
                    }
                }
                distributions.observe(this@KeyboardActivity){
                    recyclerDistributions.adapter = DistributionSelectAdapter(it){distribution, position ->
                        selectDistribution(distribution)
                        collapseRecyclerView(expandableContainer)
                    }
                }
                debts.observe(this@KeyboardActivity){ debtAdapter.setList(it) }
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
            cardDebts.isVisible = typeTransaction == TypeTransactions.PAY_DEBT
            cardAdvice.isVisible = typeTransaction != TypeTransactions.PAY_DEBT
            txtAdvice.text = typeTransaction.advice()
            btnDone.setOnClickListener {
                onFinished?.invoke(
                    keyboardViewModel.amount.value!!,
                    keyboardViewModel.distributionSelect.value,
                    keyboardViewModel.debtSelect.value
                )
                onFinished = null
                finish()
            }
            btnBack.root.setOnClickListener { finish() }
            btnSelectDistribution.imgCheckDistribution.visibility = View.GONE
            btnSelectDistribution.root.setOnClickListener {
                if (expandableContainer.isVisible) {
                    collapseRecyclerView(expandableContainer)
                } else {
                    expandRecyclerView(expandableContainer)
                }
            }
            btnSelectDebt.root.setOnClickListener {
                if (expandableContainerDebts.isVisible) {
                    collapseRecyclerView(expandableContainerDebts)
                } else {
                    expandRecyclerView(expandableContainerDebts)
                }
            }
            txtTitleToolbar.text = typeTransaction.name()
            recyclerDistributions.layoutManager = LinearLayoutManager(this@KeyboardActivity,LinearLayoutManager.VERTICAL,  false)
            recyclerDebts.layoutManager = LinearLayoutManager(this@KeyboardActivity,LinearLayoutManager.VERTICAL,  false)
            recyclerDebts.adapter = debtAdapter
        }
    }

    override fun onClick(v: View?) {
        val view = v as Button? ?: return
        keyboardViewModel.appendCharacter(view.text.toString())
    }
    private fun expandRecyclerView(expandableContainer : ConstraintLayout) {
        expandableContainer.visibility = View.VISIBLE
        expandableContainer.pivotY = 0f // Expansión desde la parte superior
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addUpdateListener { animation ->
            val scale = animation.animatedValue as Float
            expandableContainer.scaleY = scale
        }
        animator.duration = 300
        animator.start()
    }

    private fun collapseRecyclerView(expandableContainer : ConstraintLayout) {
        val animator = ValueAnimator.ofFloat(1f, 0f)
        animator.addUpdateListener { animation ->
            val scale = animation.animatedValue as Float
            expandableContainer.scaleY = scale
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator) {
                expandableContainer.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
        animator.duration = 300
        animator.start()
    }
}