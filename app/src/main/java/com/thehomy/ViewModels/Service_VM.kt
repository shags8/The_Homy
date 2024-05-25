package com.thehomy.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.thehomy.models.ServiceModel
import com.thehomy.Repository.Service_Repo
import kotlinx.coroutines.launch

class Service_VM(private val repo : Service_Repo) : ViewModel() {


    val ServiceLiveData: LiveData<ArrayList<ServiceModel>?> = repo.ServiceLiveData
    fun getData()
    {
        viewModelScope.launch {
            repo.getData()
        }
    }
}

class Service_VMFactory(private val repo : Service_Repo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Service_VM::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return Service_VM(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}