package com.estarly.wallet.presentation.screens.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.estarly.wallet.databinding.FragmentDistributionBinding
import com.estarly.wallet.presentation.adapters.DistributionAdapter
import com.estarly.wallet.presentation.dialogs.showYesOrNoAlertDialog
import com.estarly.wallet.presentation.screens.CreationDistributionActivity
import com.estarly.wallet.presentation.viewmodels.DistributionOfMoneyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DistributionFragment : Fragment() {
    private lateinit var binding : FragmentDistributionBinding
    private val distributionOfMoneyViewModel : DistributionOfMoneyViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDistributionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        getData()
    }

    private fun getData() {
        with(distributionOfMoneyViewModel) {
            getDistributions()
            getRemainingMoney()
        }
    }

    private fun initObservers() {
        with(binding) {
            with(distributionOfMoneyViewModel){
                listDistributionOfMoney.observe(viewLifecycleOwner){
                    recyclerDistribution.adapter = DistributionAdapter(
                        it,
                        onClick = { value, position ->
                            val intent = Intent(requireActivity(), CreationDistributionActivity ::class.java)
                            intent.putExtra("idDistribution",value.id)
                            startActivity(intent)
                        },
                        onDelete = {value, position ->
                            showYesOrNoAlertDialog(
                                requireContext(),
                                title = "¿Estas seguro?",
                                description = "Quieres eliminar esta distribucin '${value.name}'",
                                onPositive = {distributionOfMoneyViewModel.deleteDistribution(value.id,position) }
                            )
                        },
                        onClickCreate = {
                            startActivity(Intent(requireActivity(), CreationDistributionActivity ::class.java))
                        },
                    )
                }
                remainingMoney.observe(viewLifecycleOwner){
                    (recyclerDistribution.adapter as DistributionAdapter).setRemainingMoney(it)
                }
            }
        }
    }

    private fun initViews() {
        with(binding){
            recyclerDistribution.layoutManager = GridLayoutManager(requireContext(),2)

        }
    }
}