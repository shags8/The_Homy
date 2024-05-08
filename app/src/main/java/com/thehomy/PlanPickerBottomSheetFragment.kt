package com.thehomy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PlanPickerBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var selectedPlan: String
    private var listener: PlanSelectedListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan_picker_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val radioGroup = view.findViewById<RadioGroup>(R.id.PlanGroup)
        radioGroup.check(R.id.plan3)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)
        btnCancel.setOnClickListener {
            dismiss()
        }
        val btnOk = view.findViewById<Button>(R.id.btnOk)
        btnOk.setOnClickListener {
            val selectedPlanInt = radioGroup?.checkedRadioButtonId
            selectedPlan =
                selectedPlanInt?.let { it1 -> view.findViewById<RadioButton>(it1).text }.toString()
            if (::selectedPlan.isInitialized) {
                // Pass the selected date to the activity
                listener?.onPlanSelected(selectedPlan)
            } // Pass the selected date to the activity
            dismiss()
        }
    }

    fun setPlanSelectedListener(listener: PlanSelectedListener){
        this.listener = listener
    }
    interface PlanSelectedListener{

        fun onPlanSelected(plan:String)
    }

}