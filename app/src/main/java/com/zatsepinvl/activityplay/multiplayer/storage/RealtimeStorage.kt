package com.zatsepinvl.activityplay.multiplayer.storage

typealias  ItemChangedListener<T> = (item: T) -> Unit

interface RealtimeStorage<T> {
    fun getItem(id: String): T
    fun save(item: T)
    fun onItemChanged(listener: ItemChangedListener<T>)
}