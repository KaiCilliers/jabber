package com.example.jabbertest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class LoginViewModel : ViewModel() {
    val variableA: String = "var_A"
    val variableB: String = "var_B"
    val variableNext: String = "NEXT"

    //Data
    private val _account = MutableLiveData<String>()
    val account: LiveData<String>
        get() = _account
    fun accountName(name: String) {
        _account.value = name
    }

    // Navigation
    private val _navToChats = MutableLiveData<Boolean>()
    val navToChats: LiveData<Boolean>
        get() = _navToChats
    fun navToChats() {
        Timber.d("Navigating to chats...")
        _navToChats.value = true
    }
    fun onNavigatedToChats() {
        _navToChats.value = false
    }

    // Other functions
    fun captureAccount() {
        SharedPref.edit().putString("com.example.jabbertest.account", _account.value).commit()
    }
}