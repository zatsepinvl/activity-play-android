package com.zatsepinvl.activityplay.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreRemoteProvider :
    FirestoreProvider {
    override fun firestore(): FirebaseFirestore {
        return Firebase.firestore
    }

}