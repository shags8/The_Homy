package com.thehomy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.thehomy.AboutUs
import com.thehomy.Calendar
import com.thehomy.HelpCenter
import com.thehomy.databinding.FragmentProfileBinding

class Profile : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var URL : String
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val userId =auth.currentUser?.uid
        profile(userId.toString())
        return binding.root
    }

    private fun profile(userId:String) {
        database.reference.child("user1").child(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name =snapshot.child("userName").getValue(String::class.java)
                    val phoneNumber = snapshot.child("userPhoneNumber").getValue(String::class.java)
                    val city = snapshot.child("userCity").getValue(String::class.java)
                    val avatar = snapshot.child("avatar").getValue(Int::class.java)

                    if(avatar!=null){
                        Glide.with(this@Profile)
                            .load(avatar)
                            .apply(RequestOptions().transform(CircleCrop()))
                            .into(binding.UserProfilePhoto)
                    }


                    binding.UserName.text = "$name"
                    binding.UserPhoneNumber.text ="$phoneNumber"
                    binding.UserLocation.text ="$city"
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.AboutUs.setOnClickListener {
            val intent = Intent(activity,AboutUs::class.java)
            startActivity(intent)
        }

        remote()

        binding.HelpCenter.setOnClickListener {
            val intent = Intent(requireActivity(), HelpCenter::class.java)
            startActivity(intent)
        }
        binding.Calendar.setOnClickListener {
            Toast.makeText(
                activity,
                "This Service is not available right now",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.Share.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Check out this awesome app: $URL")
            }
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
        binding.RateAndReview.setOnClickListener {
            openAppReviewPage()
        }
    }

    private fun openAppReviewPage() {
        val appPackageName = context?.packageName // Get the package name of your app

        try {
            // Try to open the app review page in the Play Store app
            context?.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName"))
            )
        } catch (e: android.content.ActivityNotFoundException) {
            // If the Play Store app is not installed, open the review page in a web browser
            context?.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName"))
            )
        }
    }

    private fun remote(){
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600 // Adjust this for your needs
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        val defaults: Map<String, Any> = mapOf(
            "URL" to "1",
        )
        remoteConfig.setDefaultsAsync(defaults)

        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Fetch successful, now you can use the values
                URL = remoteConfig.getString("URL")
            } else {
                Toast.makeText(
                    activity,
                    "Error fetching details",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}