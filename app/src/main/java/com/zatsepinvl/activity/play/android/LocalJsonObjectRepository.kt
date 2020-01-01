package com.zatsepinvl.activity.play.android

import android.content.Context

abstract class LocalJsonObjectRepository<T : Any>(
    private val context: Context,
    private val spName: String,
    private val clazz: Class<T>
) : ObjectRepository<T> {
    override fun get(): T? {
        return context.privateSp(spName).getFromJson(clazz)
    }

    override fun exists(): Boolean {
        return context.privateSp(spName).containsJson(clazz)
    }

    override fun save(obj: T) {
        context.privateSp(spName).saveJson(obj)
    }

}