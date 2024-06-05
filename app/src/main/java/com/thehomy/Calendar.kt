package com.thehomy

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.thehomy.databinding.ActivityCalendarBinding

class Calendar : AppCompatActivity() {

    lateinit var binding: ActivityCalendarBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var isDataSaved = false // Flag to track if data has been saved
    val editTextList: MutableList<EditText> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.textView.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0)
        binding.toolbar.textView.text = "Calendar"

        binding.Add.setOnClickListener {

            Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show()
            addNewEditText(this, binding.LinearLayout)

        }
    }
    // Function to add a new EditText dynamically
    fun addNewEditText(context: Context, linearLayout: LinearLayout) {
//        val inflater = LayoutInflater.from(context)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.calendar_menu, null) as ConstraintLayout
        val newEditText = layout.findViewById<EditText>(R.id.editText)

        // Generate a unique ID for the EditText
        Toast.makeText(this, "Add2", Toast.LENGTH_SHORT).show()
        val uniqueId = View.generateViewId()
        newEditText.id = uniqueId
        Log.e("Hehehe", uniqueId.toString())
        layout.visibility = View.VISIBLE

        linearLayout.addView(layout)
        editTextList.add(newEditText)
        linearLayout.requestLayout()
    }

}