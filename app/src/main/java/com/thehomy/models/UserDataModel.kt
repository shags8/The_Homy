package com.thehomy.models

data class User(
    val userName: String,
    val userPhoneNumber: String,
    val userCity: String,
    val userEmail: String,
    val userState: String,
    val userAddress: String,
    val userAgreement: Boolean,
    val userPincode: String
)