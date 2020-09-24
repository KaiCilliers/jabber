package com.example.jabbertest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    val variableA: String = "var_A"
    val variableB: String = "var_B"
    val variableNext: String = "NEXT"

    // Navigation
    private val _navToChats = MutableLiveData<Boolean>()
    val navToChats: LiveData<Boolean>
        get() = _navToChats
    fun navToChats() {
        // TODO TIMBER LOGS
        _navToChats.value = true
    }
    fun onNavigatedToChats() {
        _navToChats.value = false
    }
}