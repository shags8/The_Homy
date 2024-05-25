package com.thehomy.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.thehomy.Repository.PlanDes_Repo
import com.thehomy.models.PlanModel
import kotlinx.coroutines.launch

class ServiceDetails_VM(private val repo : PlanDes_Repo) : ViewModel() {

    val ServiceLiveData = repo.serviceDetailsLiveData
    val imageLiveData = repo.imageLiveData
    val planLiveData: LiveData<ArrayList<PlanModel>?> = repo.planLiveData
    fun getDetails(pos:Int) {
        viewModelScope.launch {
            repo.getDetails(pos)
        }
    }

}

class ServiceDetails_VMFactory(private val repo : PlanDes_Repo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServiceDetails_VM::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return ServiceDetails_VM(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}