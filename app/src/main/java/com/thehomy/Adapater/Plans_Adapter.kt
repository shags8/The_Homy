package com.thehomy.Adapater

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thehomy.Cart
import com.thehomy.databinding.PlansCardBinding
import com.thehomy.models.PlanModel

class PlansAdapter(private val planItems: List<PlanModel>) :
    RecyclerView.Adapter<PlansAdapter.PlanViewHolder>() {

    class PlanViewHolder(private val binding: PlansCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(planItem: PlanModel, position: Int) {
            // Setting the plan name and icon
            val bulletAdapter = InnerBenefitsAdapter(planItem.bullet!!)
            binding.planName.setCompoundDrawablesWithIntrinsicBounds(2131230885, 0, 0, 0)
            binding.planName.text = planItem.planName

            // Setting the benefits list
            binding.Benefits.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = bulletAdapter
            }

            binding.Continue.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, Cart::class.java)
                intent.putExtra("pos",position+1)
                intent.putExtra("planName", planItem.planName)
                intent.putExtra("icon", planItem.icon)
                intent.putExtra("serveName", planItem.serviceName)
                intent.putExtra("serveIcon", planItem.serviceIcon)
                context.startActivity(intent)
                if (context is Activity) {
                    context.finish()
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        return PlanViewHolder(
            PlansCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return planItems.size
    }
    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val planItem = planItems[position]

        holder.bind(planItem,position)
//        holder.itemView.setOnClickListener {
//
//            val bottomSheetDialog = BottomFragment()
//            val bundle = Bundle()
//
//            bundle.putStringArrayList("list", ArrayList(planItem.bullet))
//            bottomSheetDialog.arguments = bundle
//            bottomSheetDialog.show(
//                (it.context as AppCompatActivity).supportFragmentManager,
//                bottomSheetDialog.tag
//            )
//
//        }
    }

}