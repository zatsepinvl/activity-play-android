package com.zatsepinvl.activityplay.resource

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceServiceImpl @Inject constructor(
    private  val context: Context
) : ResourceService {
    override fun colorCode(colorRes: Int): Int {
        return context.getColor(colorRes)
    }
}