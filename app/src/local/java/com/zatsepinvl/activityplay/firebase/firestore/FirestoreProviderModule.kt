package com.zatsepinvl.activityplay.firebase.firestore

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class FirestoreProviderModule {
    @Binds
    @Singleton
    abstract fun firestoreProvider(provider: FirestoreLocalProvider): FirestoreProvider
}