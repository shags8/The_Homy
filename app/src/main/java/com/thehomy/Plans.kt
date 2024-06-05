package com.thehomy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thehomy.Adapater.PlansAdapter
import com.thehomy.Repository.PlanDes_Repo
import com.thehomy.ViewModels.ServiceDetails_VM
import com.thehomy.ViewModels.ServiceDetails_VMFactory
import com.thehomy.ViewModels.SharedViewModel
import com.thehomy.databinding.FragmentDescriptionBinding
import com.thehomy.databinding.FragmentPlansBinding
import com.thehomy.models.PlanModel

class Plans : Fragment() {

    private lateinit var binding: FragmentPlansBinding
    private lateinit var sharedViewModel : SharedViewModel
    private lateinit var viewModel: ServiceDetails_VM
    private lateinit var planList: ArrayList<PlanModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentPlansBinding.inflate(inflater,container,false)

        //Setting Up Shared View Model to get the path
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        sharedViewModel.intValue.observe(viewLifecycleOwner, Observer { value ->
            viewModel.getDetails(value)
        })

        //Setting UP VM
        val repository = PlanDes_Repo()
        viewModel = ViewModelProvider(requireActivity(), ServiceDetails_VMFactory(repository))[ServiceDetails_VM::class.java]


        //Setting Up Adapter
        planList = arrayListOf()
        val planAdapter = PlansAdapter(planList)
        binding.plansList.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.plansList.adapter = planAdapter

        //Setting Up Observer
        viewModel.planLiveData.observe(viewLifecycleOwner, Observer {
            planList.clear()
            if (it != null) {
                planList.addAll(it)
            }
            planAdapter.notifyDataSetChanged()
        })





        return binding.root

    }
}