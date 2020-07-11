package com.zatsepinvl.activityplay.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore

interface FirestoreProvider {
    fun firestore(): FirebaseFirestore
}