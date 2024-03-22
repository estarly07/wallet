package com.estarly.wallet.presentation.screens.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.estarly.wallet.databinding.FragmentHistoryBinding
import com.estarly.wallet.presentation.adapters.RecordAdapter
import com.estarly.wallet.presentation.viewmodels.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private lateinit var binding : FragmentHistoryBinding
    private val historyViewModel : HistoryViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHistoryBinding.inflate(inflater,container,false)
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
            with(historyViewModel){
                records.observe(viewLifecycleOwner){
                    recyclerRecords.adapter = RecordAdapter(it)
                }
            }
        }
    }

    private fun getData() {
        with(historyViewModel){
            getRecords()
        }
    }

    private fun initViews() {
        with(binding){
            recyclerRecords.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
}