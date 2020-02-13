package com.zatsepinvl.activity.play.android.service

import android.content.Context
import com.zatsepinvl.activity.play.android.containsJson
import com.zatsepinvl.activity.play.android.getFromJson
import com.zatsepinvl.activity.play.android.privateSp
import com.zatsepinvl.activity.play.android.saveJson

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