package com.zatsepinvl.activityplay.multiplayer.storage.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreDefaultProvider : FirestoreProvider {
    override fun firestore(): FirebaseFirestore {
        return Firebase.firestore
    }

}