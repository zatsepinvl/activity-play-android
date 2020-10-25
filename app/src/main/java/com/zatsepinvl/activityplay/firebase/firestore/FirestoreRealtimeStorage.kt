package com.zatsepinvl.activityplay.firebase.firestore

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.zatsepinvl.activityplay.multiplayer.storage.ItemChangedListener
import com.zatsepinvl.activityplay.multiplayer.storage.ItemChangedSubscription
import com.zatsepinvl.activityplay.multiplayer.storage.RealtimeStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.reflect.KClass

private const val DEFAULT_COLLECTION = "default"
private const val LOG_TAG = "AP_FirestoreRealStorage"

open class FirestoreRealtimeStorage<T : Any> @Inject constructor(
    firestoreProvider: FirestoreProvider,
    private val dataType: KClass<T>,
    var collectionName: String = DEFAULT_COLLECTION
) : RealtimeStorage<T> {

    private val firestore: FirebaseFirestore = firestoreProvider.firestore()

    private val collection
        get() = firestore.collection(collectionName)

    override suspend fun getItem(itemId: String): T? {
        val snapshot = collection.document(itemId).get().await()
        return snapshot.convertToObject(dataType.java)
    }

    override suspend fun saveItem(itemId: String, item: T) {
        collection.document(itemId).set(item).await()
    }

    override suspend fun deleteItem(itemId: String) {
        collection.document(itemId).delete().await()
    }

    override fun addOnItemChangedListener(
        itemId: String,
        listener: ItemChangedListener<T>
    ): ItemChangedSubscription {
        val registration = collection.document(itemId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w(LOG_TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                checkNotNull(snapshot)
                val result = snapshot.convertToObject(dataType.java)
                listener(result)
            }
        return object : ItemChangedSubscription {
            override fun unsubscribe() {
                registration.remove()
            }
        }
    }

}

