package com.zatsepinvl.activityplay

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.zatsepinvl.activityplay.firebase.firestore.FirestoreLocalProvider
import com.zatsepinvl.activityplay.multiplayer.storage.ItemChangedListener
import com.zatsepinvl.activityplay.multiplayer.storage.firestore.FirestoreRealtimeStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

data class TestItem(
    val id: String = "",
    val payload: String = ""
)

@ExperimentalCoroutinesApi
class FirestoreRealtimeStorageTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val firestoreProvider =
        FirestoreLocalProvider(
            context
        )
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val storage = FirestoreRealtimeStorage(firestoreProvider, TestItem::class)

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun save_get_item() = runBlocking {
        val testItem = TestItem("id", "payload")
        storage.saveItem(testItem.id, testItem)
        val actualItem = storage.getItem(testItem.id)
        assertEquals(testItem, actualItem)
    }

    @Test
    fun listen_for_changes() = runBlocking {
        val testItem = TestItem("id", "payload")
        storage.saveItem(testItem.id, testItem)

        val lock = CountDownLatch(1)
        val changedItem = testItem.copy(payload = "newPayload")

        val subscription = storage.addOnItemChangedListener(
            testItem.id,
            object : ItemChangedListener<TestItem> {
                override fun onItemChanged(item: TestItem) {
                    assertEquals(changedItem, item)
                    lock.countDown()
                }
            }
        )

        storage.saveItem(changedItem.id, changedItem)
        lock.await(1, TimeUnit.SECONDS)
        subscription.unsubscribe()
    }

}