package com.thehomy

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
    private var isDateSelected = false
    private var isPlanSelected = false
    private var selectedPlan = ""   // number of times Monthly , Weekly Etc
    private var selectedDate = ""
    private var numberOfPeople = 4
    private var selectedPackage = ""  // package name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedPackage = intent.getStringExtra("planName") ?: ""
        setupUI()
        setupListeners()
    }


    private fun setupUI(){
        binding.toolbar.textView.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
        binding.toolbar.textView.text = "CART"
        binding.planTextView.text = "Monthly"
        selectedPlan = "Monthly"

        updatePrice()
        binding.Morning.isSelected = true
        if (binding.Morning.isSelected) {
            binding.MorningGroup.visibility = View.VISIBLE
            binding.MorningGroup.check(R.id.m_slot1)
        }
    }

    private fun setupListeners(){
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
            numberOfPeople=binding.numberOfPersonNumber.text.toString().toInt()
            updatePrice()
        }
        binding.decrease.setOnClickListener {
            val number = binding.numberOfPersonNumber.text
            if (number.toString().toInt() <= 4) {
                return@setOnClickListener
            }
            binding.numberOfPersonNumber.text = number.toString().toInt().minus(1).toString()
            numberOfPeople=binding.numberOfPersonNumber.text.toString().toInt()
            updatePrice()
        }
        binding.Continue.setOnClickListener {
            if (isDateSelected) {
                // Proceed with the next action
                val intent = Intent(this, PaymentsActivity::class.java)
                intent.putExtra("Price", binding.priceTextView.text.toString().toDouble().toInt())
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun toggleButtonSelection(button: Button) {
        if (!button.isSelected) {
            if (selectedButtonCount == 3) {
                return
            }
            button.isSelected = true
            selectedButtonCount++
            updatePrice()
        } else {
            if (selectedButtonCount == 1) {
                return
            }
            button.isSelected = false
            selectedButtonCount--
            updatePrice()
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
        selectedDate = date
        isDateSelected = true
    }
    override fun onPlanSelected(plan: String) {
        binding.planTextView.text = plan
        selectedPlan = plan
        updatePrice()
        isPlanSelected = true
    }

    private fun updatePrice() {
        val price = calculatePrice(selectedPlan, numberOfPeople,selectedButtonCount)
        binding.priceTextView.text = price.toString()
    }

    private fun calculatePrice(plan: String, numberOfPeople: Int, selectedTimes : Int): Double {
        val basePriceForPlan = when (selectedPackage) {
            "Homy Plus" -> when (plan) {
                "Daily" -> 1099.0
                "Weekly" -> 4616.0
                "Monthly" -> 9899.0
                "Half-Yearly" -> 49455.0
                "Yearly" -> 79129.0
                else -> 0.0
            }
            "Homy Pro" -> when (plan) {
                "Daily" -> 1999.0
                "Weekly" -> 8399.0
                "Monthly" -> 17999.0
                "Half-Yearly" -> 89999.0
                "Yearly" -> 143999.0
                else -> 0.0
            }
            else -> when (plan) {
                "Daily" -> 4999.0
                "Weekly" -> 20999.0
                "Monthly" -> 44999.0
                "Half-Yearly" -> 224999.0
                "Yearly" -> 359999.0
                else -> 0.0
            }
        }

        val additionalPeopleCharge = if (numberOfPeople > 4) {
            basePriceForPlan * 0.10 * (numberOfPeople - 4)
        } else 0.0

        return (basePriceForPlan + additionalPeopleCharge) * selectedTimes
    }
}