package com.zatsepinvl.activityplay.multiplayer.storage

@FunctionalInterface
interface ItemChangedListener<T> {
    fun onItemChanged(item: T)
}