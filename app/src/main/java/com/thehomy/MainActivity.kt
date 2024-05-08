package com.thehomy

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thehomy.Adapater.Service_Adapter
import com.thehomy.Models.ServiceModel
import com.thehomy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val serviceModel= mutableListOf<ServiceModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var navController =findNavController(R.id.fragements)
        var bottomNavigationView =findViewById<BottomNavigationView>(R.id.bottomNavBar)
        bottomNavigationView.setupWithNavController(navController)

//        serviceModel.add(ServiceModel("Kitchen King","Cooking",R.drawable.kk_icon))
//        serviceModel.add(ServiceModel("Kitchen King","Cooking",R.drawable.kk_icon))

//        binding.serviceRec.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
//        binding.serviceRec.adapter = Service_Adapter(this,serviceModel)
//
//        binding.mainToolBar.notification.setOnClickListener{
//            val intent = Intent(this,notification::class.java)
//            startActivity(intent)
//        }


    }
}