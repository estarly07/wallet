package com.estarly.wallet.presentation.screens.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.estarly.wallet.databinding.FragmentGoalsBinding
import com.estarly.wallet.presentation.adapters.GoalsAdapter
import com.estarly.wallet.presentation.dialogs.CreateGoalSheetDialog
import com.estarly.wallet.presentation.dialogs.PayGoalBottomSheetDialog
import com.estarly.wallet.presentation.viewmodels.GoalsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GoalsFragment : Fragment() {
    private val goalViewModel : GoalsViewModel by viewModels()
    private lateinit var binding : FragmentGoalsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGoalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        getData()
    }

    private fun getData() {
        with(goalViewModel){
            getGoals()
        }
    }

    private fun initObservers() {
        with(binding){
            with(goalViewModel){
                listGoals.observe(viewLifecycleOwner){ it ->
                    recyclerGoals.adapter= GoalsAdapter(it){goal->
                        showDialogPayGoal(goal)
                    }
                }
                showDialogPayGoal.observe(viewLifecycleOwner){
                    if(!it) return@observe
                    PayGoalBottomSheetDialog.showBottomSheetDialog(
                        activity = requireActivity(),
                        goalModel = goalModelUpdate!!,
                        distributions = listDistributions,
                        availableMoney  = availableMoney,
                        onPay = {amount, distribution ->
                            updateGoal(goalModelUpdate!!,amount,distribution)
                        }
                    ) {
                        dismissDialogPayGoal()
                    }
                }
            }
        }
    }

    private fun initViews() {
        with(binding){
            recyclerGoals.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

            btnCreateGoal.setOnClickListener {
                CreateGoalSheetDialog.showBottomSheetDialog(requireActivity()){name,amountInitial,amountTotal,image,description ->
                    goalViewModel.createGoal(
                        name = name,
                        amountTotal = amountTotal,
                        amountInitial = amountInitial,
                        description = description,
                        image = image
                    )
                }
            }
        }
    }

}