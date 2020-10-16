package com.zatsepinvl.activityplay.firebase.firestore

import com.google.firebase.Timestamp
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Test
import java.util.*

class FirestoreKotlinxSerializationConverterTest {

    private data class TimestampTestObject(
        val datetime: Timestamp
    )

    private data class DateTestObject(
        val datetime: Date
    )

    private data class MiscellaneousObject(
        val map: HashMap<String, String> = hashMapOf("1" to "1")
    )

    @Test
    fun convert() {
        val data = MiscellaneousObject()
        val string = Json.encodeToString(data)
        println(string)
        val obj = Json.decodeFromString<MiscellaneousObject>(string)
        println(obj)
    }
}