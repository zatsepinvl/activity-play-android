package com.zatsepinvl.activityplay.android.storage

import android.content.Context
import com.zatsepinvl.activityplay.android.containsJson
import com.zatsepinvl.activityplay.android.getFromJson
import com.zatsepinvl.activityplay.android.privateSp
import com.zatsepinvl.activityplay.android.saveJson

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