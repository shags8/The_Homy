package com.thehomy.Fragment

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thehomy.Adapater.Recipe_Adapter
import com.thehomy.Adapater.Service_Adapter
import com.thehomy.models.RecipeModel
import com.thehomy.models.ServiceModel
import com.thehomy.R
import com.thehomy.Repository.Service_Repo
import com.thehomy.ViewModels.Service_VM
import com.thehomy.ViewModels.Service_VMFactory
import com.thehomy.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var serviceModel= mutableListOf<ServiceModel>()
    private val recipeModel= mutableListOf<RecipeModel>()
    private lateinit var ServiceVM : Service_VM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        // Inflate the layout for this fragment

        //Setting Up Loading
        binding.view.visibility = View.INVISIBLE
        binding.loading.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.black))

        //Setting Up VM
        val repo = Service_Repo()
        ServiceVM = ViewModelProvider(this,Service_VMFactory(repo))[Service_VM::class.java]
        ServiceVM.getData()

        //Setting Up Adapter
        serviceModel = arrayListOf()
        val Service_Adapter = Service_Adapter(serviceModel)
        binding.serviceRec.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.serviceRec.adapter = Service_Adapter

        //Observing Data
        ServiceVM.ServiceLiveData.observe(viewLifecycleOwner){
            serviceModel.clear()
            if (it != null) {
                for (items in it) {
                    serviceModel.add(items)
                }
            }
            Service_Adapter.notifyDataSetChanged()
            //Ending the Loading
            binding.view.visibility = View.VISIBLE
            binding.loading.visibility = View.INVISIBLE
        }


        recipeModel.clear()
        recipeModel.add(RecipeModel("Aloo Tikki","Indian",R.drawable.sample_img))
        recipeModel.add(RecipeModel("Aloo Tikki","Indian",R.drawable.sample_img))
        recipeModel.add(RecipeModel("Aloo Tikki","Indian",R.drawable.sample_img))

        binding.recipeRec.layoutManager = LinearLayoutManager(context)
        binding.recipeRec.adapter = Recipe_Adapter(recipeModel)
        return binding.root
    }

}