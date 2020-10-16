package com.zatsepinvl.activityplay.firebase.firestore

import com.google.firebase.Timestamp
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*


class FirestoreMoshiConverterTest {

    private data class DateTestObject(
        val date: Date
    )

    private val converter = FirestoreMoshiConverter()

    @Test
    fun convert_from_timestamp_to_date() {
        val expectedDatetime = Date()
        val fromValue = mapOf("date" to (Timestamp(expectedDatetime)))
        val result = converter.convert(fromValue, DateTestObject::class.java)
        assertEquals(expectedDatetime, result.date)
    }

}