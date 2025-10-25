package com.example.myappmonitoringperformance.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Pantalla notificaciones"
    }
    val text: LiveData<String> = _text
}