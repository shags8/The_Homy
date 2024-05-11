package com.thehomy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.thehomy.Adapater.Service_Adapter
import com.thehomy.Models.ServiceModel
import com.thehomy.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val serviceModel= mutableListOf<ServiceModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =FragmentHomeBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
//        serviceModel.clear()
        serviceModel.add(ServiceModel("Kitchen King","Cooking",R.drawable.kk_icon))
        serviceModel.add(ServiceModel("Dust Guard","House Cleaner",R.drawable.kk_icon))

        binding.serviceRec.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.serviceRec.adapter = Service_Adapter(serviceModel)
        return binding.root
    }

}