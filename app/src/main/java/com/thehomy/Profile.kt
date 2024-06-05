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
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.thehomy.databinding.FragmentProfileBinding

class Profile : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var URL : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.AboutUs.setOnClickListener {
            val intent = Intent(activity,AboutUs::class.java)
            startActivity(intent)
        }

        remote()

        binding.HelpCenter.setOnClickListener {
            val intent = Intent(requireActivity(),HelpCenter::class.java)
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