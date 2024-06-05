package com.thehomy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.thehomy.Adapater.Benefits_Adapter
import com.thehomy.Repository.PlanDes_Repo
import com.thehomy.ViewModels.ServiceDetails_VM
import com.thehomy.ViewModels.ServiceDetails_VMFactory
import com.thehomy.ViewModels.SharedViewModel
import com.thehomy.databinding.FragmentDescriptionBinding

class Description : Fragment() {

    private lateinit var binding: FragmentDescriptionBinding
    private lateinit var sharedViewModel : SharedViewModel
    private lateinit var viewModel: ServiceDetails_VM

    private lateinit var itemList: ArrayList<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_description,container,false)

        //Setting Up Shared View Model to get the path
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        sharedViewModel.intValue.observe(viewLifecycleOwner, Observer { value ->
           viewModel.getDetails(value)
        })

        //Setting UP VM
        val repository = PlanDes_Repo()
        viewModel = ViewModelProvider(requireActivity(), ServiceDetails_VMFactory(repository))[ServiceDetails_VM::class.java]

        //Setting Up Adapter
        itemList = arrayListOf()
        val adapter = Benefits_Adapter(itemList)
        binding.Benefits.layoutManager = GridLayoutManager(activity,2)
        binding.Benefits.adapter = adapter

        //Observing Data
        viewModel.ServiceLiveData.observe(viewLifecycleOwner, Observer {
            binding.description =it
            it?.Benefits.let {
                itemList.clear()
                if (it != null) {
                    for (items in it) {

                        itemList.add(items)
                    }
                }
                adapter.notifyDataSetChanged()
            }
        }
        )


        return binding.root
    }
}