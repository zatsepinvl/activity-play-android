package com.zatsepinvl.activityplay.firebase.firestore

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FirestoreProviderModule::class])
interface FirestoreRealtimeStorageTestComponent {
    fun inject(test: FirestoreRealtimeStorageTest)
}
