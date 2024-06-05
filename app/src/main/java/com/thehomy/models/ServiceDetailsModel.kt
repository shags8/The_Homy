package com.thehomy.models

data class ServiceDetailsModel(
    val Name: String?,
    val Price:String?,
    val Img:Int?,
    val Benefits: ArrayList<String>? ,
    val desc: String?
)