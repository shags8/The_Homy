package com.thehomy.models

data class OrderResponse(
    val amount: Int,
    val amount_due: Int,
    val amount_paid: Int,
    val created_at: Int,
    val currency: String,
    val entity: String,
    val id: String,
    val status: String
)