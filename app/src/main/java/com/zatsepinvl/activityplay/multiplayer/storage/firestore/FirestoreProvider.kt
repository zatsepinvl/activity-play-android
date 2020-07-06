package com.zatsepinvl.activityplay.multiplayer.storage.firestore

import com.google.firebase.firestore.FirebaseFirestore

interface FirestoreProvider {
    fun firestore(): FirebaseFirestore
}