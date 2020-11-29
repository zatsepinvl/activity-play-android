package com.zatsepinvl.activityplay.resource

import dagger.Binds
import dagger.Module

@Module
abstract class ResourceModule {

    @Binds
    abstract fun resourceService(impl: ResourceServiceImpl): ResourceService
}