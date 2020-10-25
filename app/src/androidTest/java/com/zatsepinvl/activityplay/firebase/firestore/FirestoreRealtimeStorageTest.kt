package com.zatsepinvl.activityplay.firebase.firestore

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
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val TEST_ITEM_ID = "itemId"


data class TestItem(
    val id: String = TEST_ITEM_ID,
    val payload: String = "payload",
    val date: Date = Date(),
    val long: Long = 123412L
)

@ExperimentalCoroutinesApi
class FirestoreRealtimeStorageTest {

    @Inject
    lateinit var firestoreProvider: FirestoreProvider

    private lateinit var storage: FirestoreRealtimeStorage<TestItem>
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        DaggerFirestoreRealtimeStorageTestComponent.create().inject(this)
        storage = FirestoreRealtimeStorage(
            firestoreProvider,
            TestItem::class,
            "test_collection"
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun save_get_item() = runBlocking {
        deleteItem(TEST_ITEM_ID)
        val testItem = TestItem()
        storage.saveItem(testItem.id, testItem)
        val actualItem = storage.getItem(testItem.id)
        assertEquals(testItem, actualItem)
    }

    @Test
    fun listen_for_changes() = runBlocking {
        deleteItem(TEST_ITEM_ID)
        val testItem = TestItem()

        val lock = CountDownLatch(2)
        val changedItem = testItem.copy(payload = "newPayload")

        val subscription = storage.addOnItemChangedListener(testItem.id) { item ->
            assert(item == testItem || item == changedItem)
            lock.countDown()
        }
        storage.saveItem(testItem.id, testItem)
        storage.saveItem(changedItem.id, changedItem)
        lock.await(1, TimeUnit.SECONDS)
        subscription.unsubscribe()
    }

    private suspend fun deleteItem(itemId: String) {
        storage.deleteItem(itemId)
    }

}