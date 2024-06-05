package com.thehomy.models

data class OrderRequest(
    val amount: Int,
    val currency: String,
)