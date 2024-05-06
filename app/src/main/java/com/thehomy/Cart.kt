package com.thehomy

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thehomy.databinding.ActivityCartBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Cart : AppCompatActivity() , DatePickerBottomSheetFragment.DateSelectedListener, PlanPickerBottomSheetFragment.PlanSelectedListener{

    private lateinit var binding : ActivityCartBinding
    private var selectedButtonCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.textView.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
        binding.toolbar.textView.text = "CART"

        binding.Morning.isSelected = true
        if (binding.Morning.isSelected) {
            binding.MorningGroup.visibility = View.VISIBLE
            binding.MorningGroup.check(R.id.m_slot1)
        }

        binding.Morning.setOnClickListener {
            toggleButtonSelection(binding.Morning)
            if (binding.Morning.isSelected) {
                binding.MorningGroup.visibility = View.VISIBLE
                binding.MorningGroup.check(R.id.m_slot1)
            }
            if (!binding.Morning.isSelected) {
                binding.MorningGroup.visibility = View.GONE
            }

        }
        binding.Noon.setOnClickListener {
            toggleButtonSelection(binding.Noon)
            if (binding.Noon.isSelected) {
                binding.NoonGroup.visibility = View.VISIBLE
                binding.NoonGroup.check(R.id.n_slot1)
            }
            if (!binding.Noon.isSelected) {
                binding.NoonGroup.visibility = View.GONE
            }

        }
        binding.Evening.setOnClickListener {
            toggleButtonSelection(binding.Evening)
            if (binding.Evening.isSelected) {
                binding.EveningGroup.visibility = View.VISIBLE
                binding.EveningGroup.check(R.id.e_slot1)
            }
            if (!binding.Evening.isSelected) {
                binding.EveningGroup.visibility = View.GONE
            }

        }
        binding.dateSelector.setOnClickListener{
            showDatePickerBottomSheet()
        }
        binding.planSelector.setOnClickListener {
            showPlanPickerBottomSheet()
        }
        binding.increase.setOnClickListener {
            val number = binding.numberOfPersonNumber.text
            if (number.toString().toInt() >= 10) {
                return@setOnClickListener
            }
            binding.numberOfPersonNumber.text = number.toString().toInt().plus(1).toString()
        }
        binding.decrease.setOnClickListener {
            val number = binding.numberOfPersonNumber.text
            if (number.toString().toInt() <= 4) {
                return@setOnClickListener
            }
            binding.numberOfPersonNumber.text = number.toString().toInt().minus(1).toString()
        }
    }

    private fun toggleButtonSelection(button: Button) {
        if (!button.isSelected) {
            if (selectedButtonCount == 3) {
                return
            }
            button.isSelected = true
            selectedButtonCount++
        } else {
            if (selectedButtonCount == 1) {
                return
            }
            button.isSelected = false
            selectedButtonCount--
        }

    }

   private fun showPlanPickerBottomSheet()
   {
       val bottomSheetFragment = PlanPickerBottomSheetFragment()
       bottomSheetFragment.setPlanSelectedListener(this)
       bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
   }
   private fun showDatePickerBottomSheet()
   {
    val bottomSheetFragment = DatePickerBottomSheetFragment()
    bottomSheetFragment.setDateSelectedListener(this)
    bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
   }

    override fun onDateSelected(date: String) {
        binding.dateTextView.text = date
    }

    override fun onPlanSelected(plan: String) {
        binding.planTextView.text = plan
    }

}