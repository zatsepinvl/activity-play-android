package com.zatsepinvl.activityplay.device

import com.zatsepinvl.activityplay.gameroom.model.Device
import javax.inject.Inject

class DeviceServiceImpl @Inject constructor() : DeviceService {
    override fun getCurrentDevice(): Device {
        return Device("test", "test")
    }
}