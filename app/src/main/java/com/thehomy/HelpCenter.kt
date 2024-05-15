package com.thehomy

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.thehomy.Adapater.PlanDes_Tab_Adapter
import com.thehomy.databinding.ActivityHelpCenterBinding

class HelpCenter : AppCompatActivity() {

    private lateinit var binding: ActivityHelpCenterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHelpCenterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SetUpTabs()
    }

    fun SetUpTabs(){

        val adapter = PlanDes_Tab_Adapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            val tabNames = listOf("FAQ", "Contact Us")
            tab.text = tabNames[position]
        }).attach()

    }
}