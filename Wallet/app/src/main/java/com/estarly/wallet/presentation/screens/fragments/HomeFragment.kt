package com.estarly.wallet.presentation.screens.fragments

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.estarly.wallet.R
import com.estarly.wallet.databinding.FragmentHomeBinding
import com.estarly.wallet.domain.models.ItemGraphic
import com.estarly.wallet.domain.models.TypeTransactions
import com.estarly.wallet.presentation.adapters.GraphicItemsAdapter
import com.estarly.wallet.presentation.adapters.TransactionsAdapter
import com.estarly.wallet.presentation.dialogs.DepositSalaryBottomSheetDialog
import com.estarly.wallet.presentation.dialogs.SavingOrTakeOffMoneyBottomSheetDialog
import com.estarly.wallet.presentation.screens.DetailTransactionActivity
import com.estarly.wallet.presentation.screens.KeyboardActivity
import com.estarly.wallet.presentation.screens.MainActivity
import com.estarly.wallet.presentation.screens.TransactionsActivity
import com.estarly.wallet.presentation.viewmodels.HomeViewModel
import com.estarly.wallet.utils.animAppear
import com.estarly.wallet.utils.animVanish
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val homeViewModel : HomeViewModel by viewModels()
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
                showBalance.observe(viewLifecycleOwner){
                    if(it){
                        salaryCard.txtSalary.animAppear()
                        salaryCard.imgNoVisibleSalary.animVanish()
                        salaryCard.btnVisibilityBalance.setImageDrawable(requireActivity().resources.getDrawable(R.drawable.ic_visible))
                    }else{
                        salaryCard.btnVisibilityBalance.setImageDrawable(requireActivity().resources.getDrawable(R.drawable.ic_no_visible))
                        salaryCard.txtSalary.animVanish()
                        salaryCard.imgNoVisibleSalary.animAppear()
                    }
                }
                showDialog.observe(viewLifecycleOwner){
                    if(!it) return@observe
                    val intent = Intent(requireActivity(), KeyboardActivity :: class.java)
                    intent.putExtra(KeyboardActivity.KEY_ARGUMENT, if(isTakeOfMoney) TypeTransactions.TAKE_MONEY_OUT.name else TypeTransactions.DEPOSIT.name)
                    KeyboardActivity.onFinished = { amount, distribution, _ ->
                        homeViewModel.updateSalary(amount,null, distribution)
                    }
                    requireActivity().startActivity(intent)
                }
                showDialogPay.observe(viewLifecycleOwner){
                    if(!it) return@observe
                    if(homeViewModel.listDebts.isEmpty()){
                        Toast.makeText(requireContext(),"No hay deudas",Toast.LENGTH_SHORT).show()
                        return@observe
                    }
                    val intent = Intent(requireActivity(), KeyboardActivity :: class.java)
                    intent.putExtra(KeyboardActivity.KEY_ARGUMENT, TypeTransactions.PAY_DEBT.name)
                    KeyboardActivity.onFinished = { amount, distribution, debt->
                        debt?.let { homeViewModel.payDebt(amount,distribution, debt) }
                    }
                    requireActivity().startActivity(intent)
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
                    recyclerTransactions.adapter = TransactionsAdapter(it){
                        DetailTransactionActivity.transaction = it
                        startActivity(Intent(requireActivity(), DetailTransactionActivity::class.java))
                    }
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
            floatingsTransactions.fabDepositAutomatic.setOnClickListener {
                homeViewModel.depositAutomatic(requireContext())
            }
            floatingsTransactions.fabUserSalary.setOnClickListener {
                homeViewModel.showDialogDepositSalary()
            }
            floatingsTransactions.fabSavingTransaction.setOnClickListener {
                homeViewModel.showDialogSavingMoney()
            }

            salaryCard.btnVisibilityBalance.setOnClickListener {
                homeViewModel.changeVisibilityBalance()
            }
            btnDrawerMenu.icon.setImageResource(R.drawable.ic_menu)
            btnDrawerMenu.icon.imageTintList = ColorStateList.valueOf(resources.getColor(R.color.white))
            btnDrawerMenu.card.setCardBackgroundColor(resources.getColor(R.color.grayLight))
            btnDrawerMenu.root.setOnClickListener {MainActivity.openDrawerMenu?.openDrawerMenu() }
            buttonsActionsTransactions.btnToDeposit.setOnClickListener {homeViewModel.showDialog() }
            buttonsActionsTransactions.btnTakeOut.setOnClickListener {homeViewModel.showDialog(true) }
            floatingsTransactions.btnToPay.setOnClickListener {homeViewModel.showDialogPay() }

            graphic.recyclerIndicatorsGraphic.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            graphic.recyclerIndicatorsGraphic.adapter = GraphicItemsAdapter(listOf(
                ItemGraphic("Salario",R.color.salary) ,
                ItemGraphic("Gastado",R.color.percentageSpent) ,
                ItemGraphic("Pagos",R.color.percentagePays) ,
                ItemGraphic("Ahorro",R.color.percentageSaving) ,
            ))
            recyclerTransactions.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            txtAllTransactions.setOnClickListener { startActivity(Intent(requireActivity(), TransactionsActivity::class.java)) }
        }
    }

}