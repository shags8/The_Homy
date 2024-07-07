package com.thehomy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.thehomy.databinding.ActivityRegistrationBinding
import com.thehomy.models.User
import java.util.concurrent.TimeUnit

class Registration : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private lateinit var verificationId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SignupBtn.setOnClickListener {
//            saveUserDataToFirebase()
            if(validateUser()){
                verifyPhoneNumber()
            }

        }

    }

    private fun verifyPhoneNumber() {
        val phoneNumber = binding.userPhoneNumber.text.toString()

        if (phoneNumber.isEmpty() || phoneNumber.replace("\\D".toRegex(), "").length != 10) {
            Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
            return
        }
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$phoneNumber") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Toast.makeText(this@Registration, "Verification failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    this@Registration.verificationId = verificationId
                    // Prompt user to enter the verification code
                    promptUserForVerificationCode()
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    private fun promptUserForVerificationCode() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter Verification Code")

        // Set up the input
        val input = EditText(this)
        input.hint = "Verification Code"
        builder.setView(input)

        // Set up the buttons
        builder.setPositiveButton("Verify") { dialog, _ ->
            val code = input.text.toString()
            if (code.isEmpty()) {
                Toast.makeText(this, "Please enter the verification code", Toast.LENGTH_SHORT).show()
            } else {
                verifyCode(code)
                dialog.dismiss()
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    saveUserDataToFirebase()
                    Log.e("rishabh","Success")
                } else {
                    Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun saveUserDataToFirebase() {
//        if (validateUser()) {
            // Create a new User object with validated data

        val firebaseUser = auth.currentUser

        firebaseUser?.let {
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

            val userId = firebaseUser.uid
            val ref = database.reference.child("user1").child(userId)

            ref.setValue(user)
                .addOnCompleteListener{task->
                    if(task.isSuccessful){
                        Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Failed to save user data", Toast.LENGTH_SHORT).show()
                    }

                }
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
        if (userPhoneNumber.isEmpty()){
            Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show()
            return false
        }
        if (userEmail.isEmpty()){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
            return false
        }
        if (userAddress.isEmpty()){
            Toast.makeText(this, "Please enter a address", Toast.LENGTH_SHORT).show()
            return false
        }
        if (userCity.isEmpty()){
            Toast.makeText(this, "Please enter a City", Toast.LENGTH_SHORT).show()
            return false
        }
        if (userPincode.isEmpty()){
            Toast.makeText(this, "Please enter a pin code", Toast.LENGTH_SHORT).show()
            return false
        }
        if (userState.isEmpty()){
            Toast.makeText(this, "Please enter a state", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!userAgreement){
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