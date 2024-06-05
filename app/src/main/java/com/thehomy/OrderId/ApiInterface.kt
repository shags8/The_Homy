package com.thehomy.OrderId

import com.thehomy.models.OrderRequest
import com.thehomy.models.OrderResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiInterface {

    @POST("v1/orders")
    fun getOrder(@Header("Authorization") authorization: String,
                 @Body orderDetails: OrderRequest): Call<OrderResponse>

}