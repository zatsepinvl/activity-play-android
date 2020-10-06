package com.zatsepinvl.activityplay.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreCloudProvider @Inject constructor() : FirestoreProvider {

    override fun firestore(): FirebaseFirestore {
        return Firebase.firestore
    }
}