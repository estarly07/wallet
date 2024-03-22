package com.estarly.wallet.presentation.screens.fragments

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.estarly.wallet.R
import com.estarly.wallet.databinding.FragmentHomeBinding
import com.estarly.wallet.domain.models.ItemGraphic
import com.estarly.wallet.presentation.adapters.GraphicItemsAdapter
import com.estarly.wallet.presentation.adapters.TransactionsAdapter
import com.estarly.wallet.presentation.dialogs.DepositBottomSheetDialog
import com.estarly.wallet.presentation.dialogs.DepositSalaryBottomSheetDialog
import com.estarly.wallet.presentation.dialogs.PayBottomSheetDialog
import com.estarly.wallet.presentation.dialogs.SavingOrTakeOffMoneyBottomSheetDialog
import com.estarly.wallet.presentation.screens.MainActivity
import com.estarly.wallet.presentation.screens.TransactionsActivity
import com.estarly.wallet.presentation.viewmodels.HomeViewModel
import com.estarly.wallet.utils.animAppear
import com.estarly.wallet.utils.animRotate
import com.estarly.wallet.utils.animVanish
import com.estarly.wallet.utils.animationTranslateUp
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val homeViewModel : HomeViewModel by viewModels()
    private var isFABOpen = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        getData()
        initObservers()
    }

    private fun initObservers() {
        with(binding){
            with(homeViewModel){
                salary.observe(viewLifecycleOwner){
                    salaryCard.txtSalary.text = it
                }
                percentageSpent.observe(viewLifecycleOwner){
                    salaryCard.txtPercentageSpent.text = it
                }
                showDialog.observe(viewLifecycleOwner){
                    if(!it) return@observe
                    DepositBottomSheetDialog.showBottomSheetDialog(
                        requireActivity(),
                        homeViewModel.listDistributionOfMoney,
                        availableMoney = availableMoney,
                        isTakeOfMoney =isTakeOfMoney,
                        onDismiss = {homeViewModel.dismissDialog()},
                        onDeposit = { amount,whyTakeOfMoney, distribution ->
                            homeViewModel.updateSalary(amount,whyTakeOfMoney, distribution)
                        }
                    )
                }
                showDialogPay.observe(viewLifecycleOwner){
                    if(!it) return@observe
                    if(homeViewModel.listDebts.isEmpty()){
                        Toast.makeText(requireContext(),"No hay deudas",Toast.LENGTH_SHORT).show()
                        return@observe
                    }
                    PayBottomSheetDialog.showBottomSheetDialog(
                        requireActivity(),
                        homeViewModel.listDistributionOfMoney,
                        debts = homeViewModel.listDebts,
                        availableMoney = availableMoney,
                        onDismiss = {homeViewModel.dismissDialogPay()},
                        onPay = { amount, distribution,debt->
                            homeViewModel.payDebt(amount,distribution, debt)
                        }
                    )
                }
                showDialogSavingMoney.observe(viewLifecycleOwner){ it ->
                    if(!it) return@observe
                    SavingOrTakeOffMoneyBottomSheetDialog.showBottomSheetDialog(
                        requireActivity(),
                        homeViewModel.availableMoney,
                        onSavingMoney = { homeViewModel.savingMoney(it)},
                        onDismiss = {homeViewModel.dismissDialogSavingMoney()},
                    )
                }
                showDialogDepositSalary.observe(viewLifecycleOwner){ it ->
                    if(it == null) return@observe
                    DepositSalaryBottomSheetDialog.showBottomSheetDialog(
                        requireActivity(),
                        it,
                        onDismiss = {homeViewModel.dismissDialogDepositSalary()},
                        onDepositMoney = {salary -> homeViewModel.updateSalaryUser(salary)}
                    )
                }
                lastThreeTransactions.observe(viewLifecycleOwner){
                    transactions.recyclerTransactions.adapter = TransactionsAdapter(it)
                }
                entriesEntries.observe(viewLifecycleOwner){
                    val pieChart: PieChart = graphic.pieChart
                    val description = Description()
                    description.text = ""
                    pieChart.description = description

                    val dataSet = PieDataSet(it, "Pie Chart")
                    dataSet.colors = listOf(
                        Color.TRANSPARENT,
                        resources.getColor(R.color.salary),
                        Color.TRANSPARENT,
                        resources.getColor(R.color.percentageSpent),
                        Color.TRANSPARENT,
                        resources.getColor(R.color.percentagePays),
                        Color.TRANSPARENT,
                        resources.getColor(R.color.percentageSaving),
                    )
                    val data = PieData(dataSet)
                    pieChart.data = data
                    pieChart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD)
                    pieChart.legend.isEnabled = false //quitarle los indeces
                    data.setDrawValues(false)//quitarle los numeros de la grafica
                    pieChart.invalidate() // Refresh the chart
                }
            }
        }
    }

    private fun getData() {
        homeViewModel.getSalary()
        homeViewModel.getLastThreeTransactions()
    }

    private fun initViews() {
        with(binding) {
            floatingsTransactions.fabMain.animRotate()
            floatingsTransactions.fabDepositAutomatic.setOnClickListener {
                closeFABMenu()
                homeViewModel.depositAutomatic(requireContext())
            }
            floatingsTransactions.fabUserSalary.setOnClickListener {
                closeFABMenu()
                homeViewModel.showDialogDepositSalary()
            }
            floatingsTransactions.fabSavingTransaction.setOnClickListener {
                closeFABMenu()
                homeViewModel.showDialogSavingMoney()
            }
            floatingsTransactions.fabMain.setOnClickListener {
                if (isFABOpen) {
                    closeFABMenu()
                } else {
                    showFABMenu()
                }
                floatingsTransactions.fabMain.animRotate(isFABOpen)
            }
            btnDrawerMenu.setOnClickListener {MainActivity.openDrawerMenu?.openDrawerMenu() }
            buttonsActionsTransactions.btnToDeposit.setOnClickListener {homeViewModel.showDialog() }
            buttonsActionsTransactions.btnTakeOut.setOnClickListener {homeViewModel.showDialog(true) }
            buttonsActionsTransactions.btnToPay.setOnClickListener {homeViewModel.showDialogPay() }

            graphic.recyclerIndicatorsGraphic.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            graphic.recyclerIndicatorsGraphic.adapter = GraphicItemsAdapter(listOf(
                ItemGraphic("Salario",R.color.salary) ,
                ItemGraphic("Gastado",R.color.percentageSpent) ,
                ItemGraphic("Pagos",R.color.percentagePays) ,
                ItemGraphic("Ahorro",R.color.percentageSaving) ,
            ))
            transactions.recyclerTransactions.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            transactions.recyclerTransactions.isNestedScrollingEnabled = false

            txtAllTransactions.setOnClickListener { startActivity(Intent(requireActivity(), TransactionsActivity::class.java)) }
        }
    }
    private fun showFABMenu() {
        with(binding){
            isFABOpen = true
            Log.i("showFABMenu","showFABMenu")
            floatingsTransactions.fabDepositAutomatic.animationTranslateUp(true, hasAnimationDone = {
                floatingsTransactions.txtDepositAutomatic.animAppear()
            })
            floatingsTransactions.fabSavingTransaction.animationTranslateUp(true, hasAnimationDone = {
                floatingsTransactions.txtSavingTransaction.animAppear()
            })
            floatingsTransactions.fabUserSalary.animationTranslateUp(true, hasAnimationDone = {
                floatingsTransactions.txtUserSalary.animAppear()
            })
            floatingsTransactions.fabUserSalary.animAppear()
            floatingsTransactions.fabDepositAutomatic.animAppear()
            floatingsTransactions.fabSavingTransaction.animAppear()
        }
    }

    private fun closeFABMenu() {
        with(binding){
            Log.i("closeFABMenu","closeFABMenu")
            isFABOpen = false
            floatingsTransactions.fabDepositAutomatic.animationTranslateUp()
            floatingsTransactions.fabSavingTransaction.animationTranslateUp()
            floatingsTransactions.fabUserSalary.animationTranslateUp()
            floatingsTransactions.txtUserSalary.animVanish()
            floatingsTransactions.txtDepositAutomatic.animVanish()
            floatingsTransactions.txtSavingTransaction.animVanish()
        }
    }
}