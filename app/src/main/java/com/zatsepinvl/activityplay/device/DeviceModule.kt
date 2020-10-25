package com.zatsepinvl.activityplay.device

import dagger.Binds
import dagger.Module

@Module
abstract class DeviceModule {
    @Binds
    abstract fun deviceService(impl: DeviceServiceImpl): DeviceService
}