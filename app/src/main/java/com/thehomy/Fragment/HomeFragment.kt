package com.thehomy.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.thehomy.Adapater.Recipe_Adapter
import com.thehomy.Adapater.Service_Adapter
import com.thehomy.Models.RecipeModel
import com.thehomy.Models.ServiceModel
import com.thehomy.R
import com.thehomy.databinding.FragmentHomeBinding

@Suppress("UNREACHABLE_CODE")
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val serviceModel= mutableListOf<ServiceModel>()
    private val recipeModel= mutableListOf<RecipeModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =FragmentHomeBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        serviceModel.clear()
        serviceModel.add(ServiceModel("Kitchen King","Cooking", R.drawable.kk_icon))
        serviceModel.add(ServiceModel("Dust Guard","House Cleaner", R.drawable.dust_guard))

        binding.serviceRec.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.serviceRec.adapter = Service_Adapter(serviceModel)



        recipeModel.clear()
        recipeModel.add(RecipeModel("Aloo Tikki","Indian",R.drawable.sample_img))
        recipeModel.add(RecipeModel("Aloo Tikki","Indian",R.drawable.sample_img))
        recipeModel.add(RecipeModel("Aloo Tikki","Indian",R.drawable.sample_img))

        binding.recipeRec.layoutManager = LinearLayoutManager(context)
        binding.recipeRec.adapter = Recipe_Adapter(recipeModel)
        return binding.root
    }

}