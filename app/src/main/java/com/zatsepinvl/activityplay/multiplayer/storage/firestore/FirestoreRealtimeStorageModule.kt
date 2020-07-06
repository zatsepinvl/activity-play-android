package com.zatsepinvl.activityplay.multiplayer.storage.firestore

import dagger.Binds
import dagger.Module

@Module
abstract class FirestoreRealtimeStorageModule {
    @Binds
    abstract fun firestoreProvider(firestoreDefaultProvider: FirestoreDefaultProvider): FirestoreProvider
}