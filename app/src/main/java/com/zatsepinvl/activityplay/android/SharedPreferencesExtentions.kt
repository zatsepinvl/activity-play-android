package com.zatsepinvl.activityplay.android

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

fun Context.privateSp(name: String): SharedPreferences {
    return getSharedPreferences(name, Context.MODE_PRIVATE)
}

fun SharedPreferences.saveJson(obj: Any) {
    saveJson(obj.javaClass.name, obj)
}

@SuppressLint("ApplySharedPref")
fun SharedPreferences.saveJson(key: String, obj: Any) {
    val json = Gson().toJson(obj)
    this.edit().putString(key, json).commit()
}

inline fun <reified T> SharedPreferences.getFromJson(): T? {
    val clazz = T::class.java
    return getFromJson(clazz)
}

inline fun <reified T> SharedPreferences.getFromJson(key: String): T? {
    val clazz = T::class.java
    return getFromJson(key, clazz)
}

fun <T> SharedPreferences.getFromJson(clazz: Class<T>): T? {
    return getFromJson(clazz.name, clazz)
}

fun <T> SharedPreferences.getFromJson(key: String, clazz: Class<T>): T? {
    val json = this.getString(key, "") ?: ""
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