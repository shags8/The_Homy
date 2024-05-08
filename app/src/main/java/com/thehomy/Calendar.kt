package com.thehomy

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thehomy.databinding.ActivityCalendarBinding

class Calendar : AppCompatActivity() {

    lateinit var binding: ActivityCalendarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.textView.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
        binding.toolbar.textView.text = "Calendar"
    }
}