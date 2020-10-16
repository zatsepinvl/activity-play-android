package com.zatsepinvl.activityplay.firebase.firestore

import com.google.firebase.firestore.DocumentSnapshot


fun <T : Any> DocumentSnapshot.convertToObject(valueType: Class<T>): T {
    val converter = FirestoreMoshiConverter()
    return converter.convert(data!!, valueType)
}

