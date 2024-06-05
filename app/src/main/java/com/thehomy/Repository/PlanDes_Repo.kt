package com.thehomy.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.thehomy.models.PlanModel
import com.thehomy.models.ServiceDetailsModel

class PlanDes_Repo {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val _serviceDetailsLiveData = MutableLiveData<ServiceDetailsModel?>()
    val serviceDetailsLiveData: LiveData<ServiceDetailsModel?> = _serviceDetailsLiveData
    private val _planLiveData = MutableLiveData<ArrayList<PlanModel>?>()
    val planLiveData :LiveData<ArrayList<PlanModel>?> = _planLiveData
    private val _imageLiveData = MutableLiveData<String?>()
    val imageLiveData: LiveData<String?> = _imageLiveData



    fun getDetails(pos:Int) {
        val cardRef = database.child("Data").child("Service").child("$pos")
        cardRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("Name").getValue(String::class.java)
                val price = snapshot.child("Price").getValue(String::class.java)
                val desc = snapshot.child("desc").getValue(String::class.java)!!.replace("\\n","\n")
                val img = snapshot.child("Img").getValue(Int::class.java)
                val banner = snapshot.child("Banner").getValue(String::class.java)
                val benefitsList = ArrayList<String>()
                snapshot.child("Benefits").children.forEach { benefitSnapshot ->
                    val ar = benefitSnapshot.getValue(String::class.java)
                    Log.e("Ris","$ar")
                    benefitsList.add(benefitSnapshot.getValue(String::class.java) ?: "")
                }
                val planList = ArrayList<PlanModel>()
                snapshot.child("Plans").children.forEach{it->
                    val planName = it.child("Name").getValue(String::class.java)
                    val planIcon = it.child("Icon").getValue(Int::class.java)
                    val planBenefits = ArrayList<String>()
                    it.child("Benefits").children.forEach{planBenefit->
                        planBenefits.add(planBenefit.getValue(String::class.java)?:"")
                    }
                    val planData = PlanModel(planIcon,planName,planBenefits,name,img)
                    planList.add(planData)
                }
                val data = ServiceDetailsModel(name,price,img,benefitsList,desc)
                _serviceDetailsLiveData.postValue(data)
                _imageLiveData.postValue(banner)
                _planLiveData.postValue(planList)
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle error
                _serviceDetailsLiveData.postValue(null)
                _imageLiveData.postValue(null)
                _planLiveData.postValue(null)
            }
        })
    }
}