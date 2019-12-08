package com.zatsepinvl.activity.play.core.android

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

fun Context.privateSp(name: String): SharedPreferences {
    return getSharedPreferences(name, Context.MODE_PRIVATE)
}

fun SharedPreferences.saveJson(obj: Any) {
    val json = Gson().toJson(obj)
    this.edit().putString(obj.javaClass.name, json).apply()
}

inline fun <reified T> SharedPreferences.getFromJson(): T? {
    val clazz = T::class.java
    return getFromJson(clazz)
}

fun <T> SharedPreferences.getFromJson(clazz: Class<T>): T? {
    val json = this.getString(clazz.name, "") ?: ""
    if (json.isEmpty()) return null
    return Gson().fromJson(json, clazz)
}

inline fun <reified T> SharedPreferences.containsJson(): Boolean {
    val clazz = T::class.java
    return this.contains(clazz.name)
}

fun <T> SharedPreferences.containsJson(clazz: Class<T>): Boolean {
    return this.contains(clazz.name)
}
