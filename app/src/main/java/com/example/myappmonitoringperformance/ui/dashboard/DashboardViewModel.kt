package com.example.myappmonitoringperformance.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Pantalla Dashboard"
    }
    private val _buttonText = MutableLiveData<String>().apply {
        value = "Click para evento"
    }
    val text: LiveData<String> = _text
    val buttonText: LiveData<String> = _buttonText;
}