package com.thehomy.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.thehomy.models.ServiceModel
import com.thehomy.R

class Service_Repo {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val _ServiceLiveData = MutableLiveData<ArrayList<ServiceModel>?>()
    val ServiceLiveData: LiveData<ArrayList<ServiceModel>?> = _ServiceLiveData

    fun getData() {
        val dataRef = database.child("Data").child("Service")
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val serviceList = ArrayList<ServiceModel>()
                snapshot.children.forEach { it ->
                    val serviceName = it.child("Name").getValue(String::class.java)
                    val serviceFun = it.child("Fun").getValue(String::class.java)
                    val serviceIcon = it.child("Img").getValue(Int::class.java)
                    val product = ServiceModel(serviceName, serviceFun, R.drawable.kk_icon)
                    serviceList.add(product)
                }

                _ServiceLiveData.postValue(serviceList)
            }

            override fun onCancelled(error: DatabaseError) {
                _ServiceLiveData.postValue(null)
            }

        })
    }
}