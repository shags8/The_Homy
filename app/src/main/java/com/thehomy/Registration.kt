package com.thehomy

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.thehomy.databinding.ActivityRegistrationBinding
import com.thehomy.models.User

class Registration : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SignupBtn.setOnClickListener {
            saveUserDataToFirebase()
        }
    }

    private fun saveUserDataToFirebase() {
        if (validateUser()) {
            // Create a new User object with validated data
            val user = User(
                userName = binding.userName.text.toString(),
                userPhoneNumber = binding.userPhoneNumber.text.toString(),
                userCity = binding.userCity.text.toString(),
                userEmail = binding.userEmail.text.toString(),
                userState = binding.userState.text.toString(),
                userAddress = binding.userAddress.text.toString(),
                userAgreement = binding.userAgreement.isChecked,
                userPincode = binding.userPincode.text.toString()
            )
        }
    }

    private fun validateUser(): Boolean {
        val userName = binding.userName.text.toString()
        val userPhoneNumber = binding.userPhoneNumber.text.toString()
        val userCity = binding.userCity.text.toString()
        val userEmail = binding.userEmail.text.toString()
        val userState = binding.userState.text.toString()
        val userAddress = binding.userAddress.text.toString()
        val userAgreement = binding.userAgreement.isChecked
        val userPincode = binding.userPincode.text.toString()

        // Perform validation for each field
        if (userName.isEmpty()) {
            Toast.makeText(this, "Please fill in name", Toast.LENGTH_SHORT).show()
            return false
        }
        if (userPhoneNumber.isEmpty()) {
            Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show()
            return false
        }
        if (userEmail.isEmpty()) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
            return false
        }
        if (userAddress.isEmpty()) {
            Toast.makeText(this, "Please enter a address", Toast.LENGTH_SHORT).show()
            return false
        }
        if (userCity.isEmpty()) {
            Toast.makeText(this, "Please enter a City", Toast.LENGTH_SHORT).show()
            return false
        }
        if (userPincode.isEmpty()) {
            Toast.makeText(this, "Please enter a pin code", Toast.LENGTH_SHORT).show()
            return false
        }
        if (userState.isEmpty()) {
            Toast.makeText(this, "Please enter a state", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!userAgreement) {
            Toast.makeText(this, "Please agree to the user agreement", Toast.LENGTH_SHORT).show()
            return false
        }
        if (userPhoneNumber.replace("\\D".toRegex(), "").length != 10) {
            Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!userPincode.matches(Regex("\\d{6}"))) {
            Toast.makeText(this, "Please enter a valid 6-digit pincode", Toast.LENGTH_SHORT).show()
            return false
        }
        // All fields are valid
        return true
    }
}