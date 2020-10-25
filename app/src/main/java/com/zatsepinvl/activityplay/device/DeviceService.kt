package com.zatsepinvl.activityplay.device

import com.zatsepinvl.activityplay.gameroom.model.Device

interface DeviceService {
    fun getCurrentDevice(): Device
}