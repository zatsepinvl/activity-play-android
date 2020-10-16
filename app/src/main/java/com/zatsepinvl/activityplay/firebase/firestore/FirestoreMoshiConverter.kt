package com.zatsepinvl.activityplay.firebase.firestore

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class FirestoreMoshiConverter {
    fun <T : Any> convert(fromValue: Map<*, *>, toValueType: Class<T>): T {
        val moshi = Moshi.Builder()
            .add(FirestoreMoshiAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
        val writer = moshi.adapter(Map::class.java)
        val reader = moshi.adapter(toValueType)
        return reader.fromJson(writer.toJson(fromValue))!!
    }
}