package com.thehomy.Adapater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thehomy.R

class OnBoarding_Adapter(private val onboardingTexts: List<String>, private val onboardingImages: List<Int>):
    RecyclerView.Adapter<OnBoarding_Adapter.OnboardingViewHolder>()
    {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.onboarding_item, parent, false)
            return OnboardingViewHolder(view)
        }

        override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
            holder.textView.text = onboardingTexts[position]
            holder.imageView.setImageResource(onboardingImages[position])
        }

        override fun getItemCount(): Int = onboardingTexts.size

        class OnboardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(R.id.texts)
            val imageView: ImageView = itemView.findViewById(R.id.image)
        }
    }
