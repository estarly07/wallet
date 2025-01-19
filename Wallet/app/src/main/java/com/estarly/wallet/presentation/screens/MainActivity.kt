package com.estarly.wallet.presentation.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.estarly.wallet.R
import com.estarly.wallet.databinding.ActivityMainBinding
import com.estarly.wallet.presentation.adapters.NavigationPagerAdapter
import com.estarly.wallet.utils.setBackgroundStatus
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    companion object{
         var openDrawerMenu: OpenDrawerMenu? = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBackgroundStatus(R.color.backgroundHeader)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
            val adapter = NavigationPagerAdapter(supportFragmentManager, lifecycle)
            viewPager.adapter = adapter
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    bottomNavigationView.menu.getItem(position).isChecked = true
                }
            })

            bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_home         -> viewPager.setCurrentItem(0, true)
                    R.id.navigation_goals        -> viewPager.setCurrentItem(1, true)
                    R.id.navigation_distribution -> viewPager.setCurrentItem(2, true)
                    R.id.navigation_record       -> viewPager.setCurrentItem(3, true)
                }
                true
            }
            initDrawer()
        }
    }

    private fun initDrawer() {
        with(binding){
            with(drawerMenu){
                navCedetes    .setOnClickListener{ onTapDrawerMenuOption(navCedetes.id) }
                navDebts      .setOnClickListener{ onTapDrawerMenuOption(navDebts.id) }
                navPurchases  .setOnClickListener{ onTapDrawerMenuOption(navPurchases.id) }
                navSavingMoney.setOnClickListener{ onTapDrawerMenuOption(navSavingMoney.id) }
                navCreationAutomaticDistribution.setOnClickListener{ onTapDrawerMenuOption(navCreationAutomaticDistribution.id)}
            }

            openDrawerMenu = object : OpenDrawerMenu{
                override fun openDrawerMenu() {
                    drawerLayout.openDrawer(navView, true)
                }
            }
        }
    }

    private fun onTapDrawerMenuOption(idView : Int) {
        when(idView){
            R.id.nav_cedetes ->{ startActivity(Intent(this@MainActivity, CDTActivity::class.java))}
            R.id.nav_debts ->{ startActivity(Intent(this@MainActivity, DebtsActivity::class.java))}
            R.id.nav_purchases ->{startActivity(Intent(this@MainActivity, PendingPurchasesActivity::class.java))}
            R.id.nav_saving_money ->{startActivity(Intent(this@MainActivity, SavingMoneyActivity::class.java))}
            R.id.nav_creation_automatic_distribution ->{ startActivity(Intent(this@MainActivity, CreationAutomationDistributionActivity::class.java)) }
        }
        binding.drawerLayout.closeDrawers()
    }

    override fun onBackPressed() {
        with(binding){
            if (viewPager.currentItem == 0) {
                super.onBackPressed()
            } else {
                viewPager.currentItem = viewPager.currentItem - 1
            }
        }
    }
}