package com.zatsepinvl.activityplay.firebase.firestore

import com.google.firebase.Timestamp
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.util.*

class FirestoreMoshiAdapter {
    @ToJson
    fun dateToString(date: Date): String = date.toString()

    @FromJson
    fun dateFromTimestamp(timestamp: Timestamp): Date = timestamp.toDate()
}