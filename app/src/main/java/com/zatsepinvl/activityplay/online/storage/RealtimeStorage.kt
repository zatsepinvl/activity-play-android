package com.zatsepinvl.activityplay.online.storage

typealias  ItemChangedListener<T> = (item: T) -> Unit

interface RealtimeStorage<T> {
    fun getItem(id: String): T
    fun save(item: T)
    fun onItemChanged(listener: ItemChangedListener<T>)
}