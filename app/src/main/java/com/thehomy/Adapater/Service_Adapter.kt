package com.thehomy.Adapater

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thehomy.Models.ServiceModel
import com.thehomy.databinding.ServiceItemBinding

class Service_Adapter(private val items:List<ServiceModel>):RecyclerView.Adapter<Service_Adapter.ServiceViewModel>() {


    class ServiceViewModel(private val binding: ServiceItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: ServiceModel){
            binding.ServiceName.text =item.serviceName
            binding.serviceFunc.text = item.serviceFunc
            item.serviceImg?.let {
                binding.ServiceImg.setImageResource(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewModel {

        return ServiceViewModel(ServiceItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ServiceViewModel, position: Int) {
        val item = items[position]
        holder.bind(item)
    }
}