package com.ucb.coffespot

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoffeeSpotApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Inicializa Firebase (lee google-services.json automáticamente)
        FirebaseApp.initializeApp(this)
    }

}