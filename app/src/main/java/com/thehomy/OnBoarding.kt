package com.thehomy

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thehomy.Adapater.OnBoarding_Adapter
import com.thehomy.databinding.ActivityOnBoardingBinding
import java.util.Timer
import java.util.TimerTask

class OnBoarding : AppCompatActivity() {

    lateinit var binding : ActivityOnBoardingBinding
    private val onboardingTexts = listOf( "We Cook , You Savor", "We Clean , You Relax","We Scrub , You Shine")
    private val onboardingImages = listOf(R.drawable.onboard_cook,R.drawable.onboard_cleaner,R.drawable.onboard_toilet)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Animation()
    }
    fun Animation(){
        binding.onboardingVP.adapter = OnBoarding_Adapter(onboardingTexts,onboardingImages)
        binding.onboardingVP.isUserInputEnabled = false
        val NUM_PAGES = 3
        var currentPage = 0

        val timer = Timer()
        val update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            binding.onboardingVP.setCurrentItem(currentPage++ , true) // Smoothly scroll to the next page
        }
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread(update)
            }
        }, 0, 2500)
    }
}