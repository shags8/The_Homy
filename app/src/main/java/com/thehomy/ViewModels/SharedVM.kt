package com.thehomy.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val intValue = MutableLiveData<Int>()
}