package com.thehomy

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.thehomy.Adapater.PlanDes_Tab_Adapter
import com.thehomy.Repository.PlanDes_Repo
import com.thehomy.ViewModels.ServiceDetails_VM
import com.thehomy.ViewModels.ServiceDetails_VMFactory
import com.thehomy.ViewModels.SharedViewModel
import com.thehomy.databinding.ActivityPlanDesBinding

class PlanDes : AppCompatActivity() {

    private lateinit var binding: ActivityPlanDesBinding
    private lateinit var viewModel: ServiceDetails_VM
    private lateinit var sharedViewModel : SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this,R.layout.activity_plan_des)

        // Setting up the Shared view model to send path
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        val pos = intent.getIntExtra("pos", 0)
        sharedViewModel.intValue.value = pos

        //Setting up the Tabs
        SetUpTabs()

        //Setting UP VM
        val repository = PlanDes_Repo()
        viewModel = ViewModelProvider(this, ServiceDetails_VMFactory(repository))[ServiceDetails_VM::class.java]

        //Observing Data
        viewModel.imageLiveData.observe(this) {
            Glide.with(this).load(it).into(binding.displayImage)
        }

        binding.toolbar.appCompatImageButton.setOnClickListener {
            finish()
        }



    }

    private fun SetUpTabs(){

        val adapter = PlanDes_Tab_Adapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager,TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            val tabNames = listOf("Description", "Plans")
            tab.text = tabNames[position]
        }).attach()

    }
}