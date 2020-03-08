package com.zatsepinvl.activity.play

import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE
import com.zatsepinvl.activity.play.di.DaggerApplicationComponent
import com.zatsepinvl.activity.play.language.service.AppLanguageService
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject


open class ActivityPlayApplication : DaggerApplication() {

    @Inject
    lateinit var languageService: AppLanguageService

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        languageService.init(this)
        initAds()
    }

    private fun initAds() {
        val config = RequestConfiguration.Builder()
            .setTagForChildDirectedTreatment(TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE)
            .build()
        MobileAds.setRequestConfiguration(config)
        MobileAds.initialize(this) {}
    }
}