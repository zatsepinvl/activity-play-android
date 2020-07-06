package com.zatsepinvl.activityplay.multiplayer.storage

interface RealtimeStorage<T> {
    suspend fun getItem(itemId: String): T?
    suspend fun saveItem(itemId: String, item: T)
    fun addOnItemChangedListener(
        itemId: String,
        listener: ItemChangedListener<T>
    ): ItemChangedSubscription
}