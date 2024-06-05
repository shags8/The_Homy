package com.thehomy

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import com.thehomy.OrderId.RetrofitApi
import com.thehomy.models.OrderRequest
import com.thehomy.models.OrderResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentsActivity : AppCompatActivity(), PaymentResultWithDataListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payments)

        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0 // Adjust this for your needs
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val apiKeyId = remoteConfig.getString("apikey")
                val apiKeySecret = remoteConfig.getString("apikeysecret")
                // Construct the authorization header
                val authHeaderValue = "Basic " + Base64.encodeToString("$apiKeyId:$apiKeySecret".toByteArray(), Base64.NO_WRAP)

                // Fetch the price from intent
                val price = intent.getIntExtra("Price", 9999)
                val orderDetails = OrderRequest(price * 100, "INR")
                val call = RetrofitApi.apiInterface.getOrder(authHeaderValue, orderDetails)
                call.enqueue(object : Callback<OrderResponse> {
                    override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                        if (response.isSuccessful) {
                            val orderId = response.body()?.id

                            orderId?.let { startRazorpayPayment(it) }
                        }
                    }

                    override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    }
                })

            } else {
                // Handle failure to fetch Remote Config values
                Toast.makeText(this, "Failed to fetch Remote Config values", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun startRazorpayPayment(orderId : String) {
        // Ensure orderId is available before starting the payment process
        val key = "rzp_test_MeSeOad4Zmqmlt"
        Checkout.preload(applicationContext)
        val co = Checkout()
        co.setKeyID(key)

        try {
            val option = JSONObject().apply {
                put("name", "The Homy")
                put("currency", "INR")
                put("theme.color", "#8D27CB")
                put("order_id", orderId)
            }

            co.open(this@PaymentsActivity, option)

        } catch (e: Exception) {
            Toast.makeText(this@PaymentsActivity, "Some error occurred", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        TODO("Not yet implemented")
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        TODO("Not yet implemented")
    }
}