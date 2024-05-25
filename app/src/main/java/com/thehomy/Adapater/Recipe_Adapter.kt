package com.thehomy.Adapater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thehomy.models.RecipeModel
import com.thehomy.databinding.RecipeItemBinding

class Recipe_Adapter(private val items: List<RecipeModel>): RecyclerView.Adapter<Recipe_Adapter.RecipeViewHolder>() {
    class RecipeViewHolder(private val binding: RecipeItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecipeModel) {
            binding.recipeTitle.text = item.recipeName
            binding.recipeDesc.text = item.recipeDescription

            item.recipeImage?.let {
                binding.recipeImage.setImageResource(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

}