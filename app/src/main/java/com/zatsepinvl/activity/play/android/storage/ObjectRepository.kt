package com.zatsepinvl.activity.play.android.storage

interface ObjectRepository<T> {
    fun save(obj: T)
    fun get(): T?
    fun exists(): Boolean
}