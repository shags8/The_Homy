package com.thehomy.OrderId

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {
    private val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.razorpay.com/")
            .build()
    }
    val apiInterface: ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
}