package com.thehomy.Adapater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thehomy.databinding.ItemBullletlistBinding

class InnerBenefitsAdapter(private val bulletPoints: List<String>): RecyclerView.Adapter<InnerBenefitsAdapter.InnerViewHolder>() {

    class InnerViewHolder(private val binding: ItemBullletlistBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(bulletPoint: String) {
            binding.points.text = bulletPoint
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        return InnerViewHolder(ItemBullletlistBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return bulletPoints.size
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        val bulletPoint = bulletPoints[position]
        holder.bind(bulletPoint)
    }
}