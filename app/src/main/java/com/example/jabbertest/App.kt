package com.example.jabbertest

import android.app.Application
import android.content.res.Configuration
import androidx.preference.PreferenceManager
import timber.log.Timber

class App : Application() {

    // Called when the application is starting, before any other application objects have been created.
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.d("App - Created...")

        SharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        Timber.d("Shared Preferences initialised...")
        someDataForSharedPref()
    }

    // Called by the system when the device configuration changes while your component is running.
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Timber.d("App - Configuration change...")
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    override fun onLowMemory() {
        super.onLowMemory()
        Timber.d("App - Low Memory...")
    }

    private fun someDataForSharedPref() {
        SharedPref.edit().putString("data","I got you some data!").commit()
    }
}