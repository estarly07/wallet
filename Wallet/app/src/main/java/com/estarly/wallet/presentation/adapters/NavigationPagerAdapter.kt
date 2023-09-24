package com.estarly.wallet.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.estarly.wallet.presentation.screens.fragments.DistributionFragment
import com.estarly.wallet.presentation.screens.fragments.GoalsFragment
import com.estarly.wallet.presentation.screens.fragments.HistoryFragment
import com.estarly.wallet.presentation.screens.fragments.HomeFragment

class NavigationPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :FragmentStateAdapter(fragmentManager, lifecycle) {
    private val homeFragment    = HomeFragment()
    private val goalsFragment   = GoalsFragment()
    private val historyFragment = HistoryFragment()
    private val distributionFragment = DistributionFragment()

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> homeFragment
            1 -> goalsFragment
            2 -> distributionFragment
            3 -> historyFragment
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}
