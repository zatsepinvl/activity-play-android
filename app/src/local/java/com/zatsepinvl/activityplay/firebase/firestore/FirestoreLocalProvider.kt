package com.zatsepinvl.activityplay.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Singleton

private const val FIRESTORE_LOCAL_HOST = "10.0.2.2:8348"

@Singleton
class FirestoreLocalProvider : FirestoreProvider {
    init {
        val settings = FirebaseFirestoreSettings.Builder()
            //10.0.2.2 - IP address of the host machine for the Android Emulator
            .setHost(FIRESTORE_LOCAL_HOST)
            .setSslEnabled(false)
            .setPersistenceEnabled(false)
            .build()

        val firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = settings
    }

    override fun firestore(): FirebaseFirestore {
        return Firebase.firestore
    }

}